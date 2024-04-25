package com.blockchain.blochainapp.data.functionality.role.service;

import com.blockchain.blochainapp.data.functionality.role.domain.Role;

import java.util.Collection;
import java.util.Set;

public interface RoleQueryService {
    Set<Role> getAllByNames(Collection<String> names);
}
