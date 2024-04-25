package com.blockchain.blochainapp.security.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtHelper {

    public static final String JWT_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String SUBJECT = "sub";

    public static String removeBearerText(String jwt) {
        return jwt.replace(JWT_PREFIX, "");
    }

    public static String getClientId(HttpServletRequest request) {
        return getCookieValue(request, CookieHelper.CLIENT_ID);
    }

    public static String getRefreshToken(HttpServletRequest request) {
        return getCookieValue(request, CookieHelper.REFRESH_TOKEN);
    }

    public static String getSubject(Claims claims) {
        return Optional.ofNullable(claims.get(SUBJECT))
                .map(Object::toString)
                .orElseThrow(() -> new JwtException("Subject not exist in JWT token"));
    }

    private static String getCookieValue(HttpServletRequest request, String cookieName) {
        var cookies = request.getCookies();
        var clientIdCookie = Stream.of(cookies)
                .filter(cookie -> Objects.equals(cookie.getName(), cookieName))
                .findFirst()
                .orElseThrow(() -> new JwtException("Cannot find " + cookieName));

        return Optional.ofNullable(clientIdCookie.getValue())
                .orElseThrow(() -> new JwtException("Cannot resolve value for cookie: " + cookieName));
    }

}
