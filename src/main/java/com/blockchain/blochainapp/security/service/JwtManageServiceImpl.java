package com.blockchain.blochainapp.security.service;

import com.blockchain.blochainapp.security.helper.JwtHelper;
import com.blockchain.blochainapp.security.model.AuthenticatedUser;
import com.blockchain.blochainapp.security.properties.JwtProperties;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class JwtManageServiceImpl implements JwtManageService {

    private final JwtProperties jwtProperties;

    @Override
    public String createAccessToken(AuthenticatedUser user, String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setAudience(jwtProperties.getAudience())
                .setIssuer(jwtProperties.getIssuer())
                .setId(UUID.randomUUID().toString())
                .claim("roles", user.getAuthorities())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getKey())
                .compact();
    }

    @Override
    public String createRefreshToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setAudience(jwtProperties.getAudience())
                .setIssuer(jwtProperties.getIssuer())
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getKey())
                .compact();
    }

    @Override
    public boolean isExpired(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperties.getKey()).parseClaimsJws(JwtHelper.removeBearerText(token));
            return false;
        } catch (ExpiredJwtException ex) {
            return true;
        }
    }

    @Override
    public boolean verify(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperties.getKey()).parseClaimsJws(JwtHelper.removeBearerText(token));
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getKey()).parseClaimsJws(JwtHelper.removeBearerText(token)).getBody();
    }

}
