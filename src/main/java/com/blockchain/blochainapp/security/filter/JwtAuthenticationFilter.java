package com.blockchain.blochainapp.security.filter;

import com.blockchain.blochainapp.data.functionality.user.service.UserQueryService;
import com.blockchain.blochainapp.security.cache.CurrentSessionCache;
import com.blockchain.blochainapp.security.helper.JwtHelper;
import com.blockchain.blochainapp.security.model.AuthenticatedUser;
import com.blockchain.blochainapp.security.service.JwtManageService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.blockchain.blochainapp.security.helper.JwtHelper.TOKEN_HEADER;


public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtManageService jwtManageService;
    private final UserQueryService userQueryService;
    private final CurrentSessionCache currentSessionCache;
    private final List<String> authList;

    public JwtAuthenticationFilter(JwtManageService jwtManageService, UserQueryService userQueryService, CurrentSessionCache currentSessionCache, String[] authList) {
        this.jwtManageService = jwtManageService;
        this.userQueryService = userQueryService;
        this.currentSessionCache = currentSessionCache;
        this.authList = List.of(authList);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return authList.stream()
                .anyMatch(authSkipPath -> authSkipPath.contains("**")
                        ? request.getServletPath().startsWith(authSkipPath.replace("**", ""))
                        : Objects.equals(request.getServletPath(), authSkipPath));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var jwtToken = request.getHeader(TOKEN_HEADER);
        try {
            verifyToken(jwtToken, request, response);
        } catch (JwtException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        filterChain.doFilter(request, response);
    }

    private void verifyToken(String jwtToken, HttpServletRequest request, HttpServletResponse response) {
        if (Objects.isNull(jwtToken) || !jwtManageService.verify(jwtToken)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        var claims = jwtManageService.getClaims(jwtToken);
        var subject = JwtHelper.getSubject(claims);
        var session = currentSessionCache.checkAndGetSession(subject, JwtHelper.getClientId(request));
        var user = userQueryService.getOptByUsername(session.username())
                .orElseThrow(() -> new JwtException("Cannot find user with username: " + session.username()));
        var authenticatedUser = new AuthenticatedUser(user);
        var authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
