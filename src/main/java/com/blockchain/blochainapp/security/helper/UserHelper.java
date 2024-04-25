package com.blockchain.blochainapp.security.helper;

import com.blockchain.blochainapp.security.model.AuthenticatedUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserHelper {

    public static AuthenticatedUser getAuthenticatedUser() {
        return ((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}
