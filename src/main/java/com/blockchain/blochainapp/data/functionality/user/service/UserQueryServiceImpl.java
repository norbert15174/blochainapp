package com.blockchain.blochainapp.data.functionality.user.service;

import com.blockchain.blochainapp.data.functionality.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional(readOnly = true)
@AllArgsConstructor
@Service
class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getOptByUsername(String username) {
        return userRepository.findByUsername(username.toLowerCase());
    }

    @Override
    public Optional<User> getOptByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase());
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean isNameExist(String name, Optional<Long> idOpt) {
        var userOpt = getOptByUsername(name);
        return idOpt
                .map(aLong -> userOpt.map(user -> !Objects.equals(user.getId(), aLong))
                        .orElse(false))
                .orElseGet(userOpt::isPresent);
    }

    @Override
    public boolean isEmailExist(String name, Optional<Long> idOpt) {
        var userOpt = getOptByEmail(name);
        return idOpt
                .map(aLong -> userOpt.map(user -> !Objects.equals(user.getId(), aLong))
                        .orElse(false))
                .orElseGet(userOpt::isPresent);
    }
}
