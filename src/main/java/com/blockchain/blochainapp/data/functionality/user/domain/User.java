package com.blockchain.blochainapp.data.functionality.user.domain;

import com.blockchain.blochainapp.data.functionality.common.domain.EntityId;
import com.blockchain.blochainapp.data.functionality.role.domain.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User implements EntityId<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String lastname;
    private String email;
    private LocalDate createdAt = LocalDate.now();
    private boolean enabled;
    private String walletAddress;
    private String publicKey;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }

    public void addRole(Role role) {
        this.getRoles().add(role);
        role.getUsers().add(this);
    }
}
