package com.blockchain.blochainapp.data.functionality.role.service;

import com.blockchain.blochainapp.data.functionality.role.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.name in :names")
    Set<Role> findAllByNames(@Param("names") Collection<String> names);

}
