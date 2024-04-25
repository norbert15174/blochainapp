package com.blockchain.blochainapp.security.helper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieHelper {

    public final static String REFRESH_TOKEN = "eDostawczak.RefreshToken";
    public final static String CLIENT_ID = "eDostawczak.ClientId";

    public static Cookie createCookie(HttpServletRequest httpServletRequest, String name, String value) {
        var cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

}
