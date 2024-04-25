package com.blockchain.blochainapp.core.user.service;

import com.blockchain.blochainapp.core.user.dto.RegisterDTO;
import com.blockchain.blochainapp.core.user.mapper.UserMapper;
import com.blockchain.blochainapp.data.functionality.role.domain.Role;
import com.blockchain.blochainapp.data.functionality.role.service.RoleQueryService;
import com.blockchain.blochainapp.data.functionality.user.service.UserCudService;
import com.blockchain.blochainapp.security.helper.PasswordHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserCudService userCudService;
    private final RoleQueryService roleQueryService;
    private final PasswordHelper passwordHelper;

    @Override
    public RegisterDTO register(RegisterDTO dto) {
        var encryptedPassword = passwordHelper.encryptPassword(dto.password());
        var user = UserMapper.build(dto, encryptedPassword);
        var roles = roleQueryService.getAllByNames(dto.roles());
        user.addRoles(roles);
        var created = userCudService.create(user);
        var userRoles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        return RegisterDTO.builder()
                .username(created.getUsername())
                .name(created.getName())
                .lastname(created.getLastname())
                .password(created.getPassword())
                .email(created.getEmail())
                .roles(userRoles)
                .build();
    }

}
