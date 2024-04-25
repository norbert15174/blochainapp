package com.blockchain.blochainapp.data.functionality.user.service;


import com.blockchain.blochainapp.data.functionality.common.domain.CheckUnique;
import com.blockchain.blochainapp.data.functionality.user.domain.User;

import java.util.Optional;

public interface UserQueryService extends CheckUnique {
    Optional<User> getOptByUsername(String username);

    Optional<User> getOptByEmail(String email);
}
