package com.blockchain.blochainapp.api.user.response;

import lombok.Builder;

import java.util.Collection;

@Builder
public record UserInfoResponse(String name, String lastname, String address) {
}
