package com.blockchain.blochainapp.security.dto;

import lombok.*;

@Builder
public record UserLoginDTO(String username, String password) {

}
