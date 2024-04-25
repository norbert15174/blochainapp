package com.blockchain.blochainapp.api.user.controller;

import com.blockchain.blochainapp.api.user.request.LoginRequest;
import com.blockchain.blochainapp.api.user.request.RegisterRequest;
import com.blockchain.blochainapp.api.user.response.UserResponse;
import com.blockchain.blochainapp.core.user.dto.RegisterDTO;
import com.blockchain.blochainapp.core.user.service.AccountService;
import com.blockchain.blochainapp.security.dto.TokenDTO;
import com.blockchain.blochainapp.security.dto.UserLoginDTO;
import com.blockchain.blochainapp.security.service.UserLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AccountController {

    private final AccountService accountService;
    private final UserLoginService userLoginService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        var dto = RegisterDTO.builder()
                .username(request.getUsername())
                .name(request.getName())
                .lastname(request.getLastname())
                .password(request.getPassword())
                .email(request.getEmail())
                .roles(request.getRoles())
                .build();

        var created = accountService.register(dto);
        var response = UserResponse.builder()
                .username(request.getUsername())
                .name(request.getName())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .roles(created.roles())
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        var dto = UserLoginDTO.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();

        return ResponseEntity.ok(userLoginService.login(dto, httpServletRequest, httpServletResponse));
    }

}
