package com.blockchain.blochainapp.security.helper;

import com.blockchain.blochainapp.security.properties.AccountProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PasswordHelper {

    private final AccountProperties accountProperties;
    private final PasswordEncoder passwordEncoder;

    public String encryptPassword(String password) {
        var saltedAndPepperedPassword = accountProperties.getSalt() + password + accountProperties.getPepper();
        return passwordEncoder.encode(saltedAndPepperedPassword);
    }

}
