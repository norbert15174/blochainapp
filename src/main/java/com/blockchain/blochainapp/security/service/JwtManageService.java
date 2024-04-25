package com.blockchain.blochainapp.security.service;

import com.blockchain.blochainapp.security.model.AuthenticatedUser;
import io.jsonwebtoken.Claims;

public interface JwtManageService {
    String createAccessToken(AuthenticatedUser user, String subject);

    String createRefreshToken(String username);

    boolean isExpired(String token);

    boolean verify(String token);

    Claims getClaims(String jwtToken);
}
