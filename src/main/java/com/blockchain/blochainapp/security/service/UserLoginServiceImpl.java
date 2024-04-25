package com.blockchain.blochainapp.security.service;

import com.blockchain.blochainapp.data.functionality.user.domain.User;
import com.blockchain.blochainapp.data.functionality.user.service.UserQueryService;
import com.blockchain.blochainapp.security.cache.CurrentSessionCache;
import com.blockchain.blochainapp.security.dto.TokenDTO;
import com.blockchain.blochainapp.security.dto.UserLoginDTO;
import com.blockchain.blochainapp.security.exception.InvalidTokenException;
import com.blockchain.blochainapp.security.helper.CookieHelper;
import com.blockchain.blochainapp.security.helper.JwtHelper;
import com.blockchain.blochainapp.security.model.AuthenticatedUser;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.blockchain.blochainapp.security.helper.JwtHelper.TOKEN_HEADER;
import static com.blockchain.blochainapp.security.helper.JwtHelper.getRefreshToken;

@AllArgsConstructor
@Service
class UserLoginServiceImpl implements UserLoginService {

    private final UserQueryService userQueryService;
    private final JwtManageService jwtManageService;
    private final CurrentSessionCache currentSessionCache;

    @Override
    public TokenDTO login(UserLoginDTO dto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        var user = userQueryService.getOptByUsername(dto.username()).orElseThrow(EntityNotFoundException::new);
        return generateToken(httpServletRequest, httpServletResponse, user);
    }

    @Override
    public TokenDTO refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            var jwtToken = Optional.ofNullable(httpServletRequest.getHeader(TOKEN_HEADER))
                    .map(JwtHelper::removeBearerText)
                    .orElseThrow(() -> new InvalidTokenException("Empty authorization header"));

            var refreshToken = getRefreshToken(httpServletRequest);

            if (jwtManageService.isExpired(refreshToken)) {
                throw new InvalidTokenException("Token expired");
            }

            var claims = jwtManageService.getClaims(refreshToken);
            var subject = JwtHelper.getSubject(claims);
            var session = currentSessionCache.checkAndGetSession(subject, JwtHelper.getClientId(httpServletRequest));

            if (!jwtToken.startsWith(session.shortAccessToken())) {
                throw new InvalidTokenException("Invalid access token");
            }

            if (!Objects.equals(refreshToken, session.refreshToken())) {
                throw new InvalidTokenException("Invalid refresh token");
            }

            var user = userQueryService.getOptByUsername(session.username()).orElseThrow(EntityNotFoundException::new);
            return generateToken(httpServletRequest, httpServletResponse, user);
        } catch (JwtException ex) {
            throw new InvalidTokenException(ex.getMessage());
        }
    }

    private TokenDTO generateToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, User user) {
        var subject = String.format("users/%d", user.getId());
        var accessToken = jwtManageService.createAccessToken(new AuthenticatedUser(user), subject);
        var refreshToken = jwtManageService.createRefreshToken(subject);
        var clientId = UUID.randomUUID().toString().replace("-", "");

        httpServletResponse.addCookie(CookieHelper.createCookie(httpServletRequest, CookieHelper.REFRESH_TOKEN, refreshToken));
        httpServletResponse.addCookie(CookieHelper.createCookie(httpServletRequest, CookieHelper.CLIENT_ID, clientId));

        currentSessionCache.createOrReplaceSession(accessToken, refreshToken, clientId, subject, user.getUsername());

        return TokenDTO.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .clientId(clientId)
                .build();
    }


}
