package com.blockchain.blochainapp.api.user.controller;

import com.blockchain.blochainapp.security.dto.TokenDTO;
import com.blockchain.blochainapp.security.handler.SecurityException;
import com.blockchain.blochainapp.security.service.UserLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityException
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AccountSecurityController {

    private final UserLoginService userLoginService;

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDTO> refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return ResponseEntity.ok(userLoginService.refreshToken(httpServletRequest, httpServletResponse));
    }

}
