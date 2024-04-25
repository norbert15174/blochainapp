package com.blockchain.blochainapp.core.user.mapper;

import com.blockchain.blochainapp.core.user.dto.RegisterDTO;
import com.blockchain.blochainapp.data.functionality.user.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static User build(RegisterDTO dto, String encodedPassword) {
        var user = new User();
        user.setName(dto.name());
        user.setLastname(dto.lastname());
        user.setUsername(dto.username());
        user.setPassword(encodedPassword);
        user.setEmail(dto.email());
        return user;
    }

}
