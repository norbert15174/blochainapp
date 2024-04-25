package com.blockchain.blochainapp.security.service;

import com.blockchain.blochainapp.security.dto.TokenDTO;
import com.blockchain.blochainapp.security.dto.UserLoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserLoginService {
    TokenDTO login(UserLoginDTO dto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    TokenDTO refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
