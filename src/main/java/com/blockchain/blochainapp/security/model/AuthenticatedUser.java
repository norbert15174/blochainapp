package com.blockchain.blochainapp.security.model;

import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import static com.blockchain.blochainapp.security.helper.UserAuthorityHelper.getUserAuthorities;

public class AuthenticatedUser extends User {

    @Getter
    private final com.blockchain.blochainapp.data.functionality.user.domain.User user;

    public AuthenticatedUser(com.blockchain.blochainapp.data.functionality.user.domain.User user) {
        super(user.getUsername(), user.getPassword(), getUserAuthorities(user));
        this.user = user;
    }

}
