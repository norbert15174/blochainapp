package com.blockchain.blochainapp.api.user.controller;

import com.blockchain.blochainapp.api.user.response.UserInfoResponse;
import com.blockchain.blochainapp.data.functionality.user.service.UserQueryService;
import com.blockchain.blochainapp.security.helper.UserHelper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserQueryService userQueryService;

    @GetMapping("/users")
    public ResponseEntity<List<UserInfoResponse>> getAll() {
        var users = userQueryService.getAll()
                .stream()
                .filter(user -> !UserHelper.getAuthenticatedUser().getUser().getUsername().equals(user.getUsername()))
                .map(user -> UserInfoResponse.builder()
                        .address(user.getWalletAddress())
                        .lastname(user.getLastname())
                        .name(user.getName())
                        .build())
                .toList();
        return ResponseEntity.ok(users);
    }

}
