package com.blockchain.blochainapp.api.user.request;

import com.blockchain.blochainapp.core.validator.annotation.CheckUniqueName;
import com.blockchain.blochainapp.data.functionality.user.service.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class RegisterRequest {

    @CheckUniqueName(queryService = UserQueryService.class)
    private String username;
    private String password;
    private String email;
    private String name;
    private String lastname;
    private Set<String> roles;

}
