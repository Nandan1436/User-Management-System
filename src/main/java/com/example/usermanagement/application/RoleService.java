package com.example.usermanagement.application;

import com.example.usermanagement.application.interfaces.RoleRepository;
import com.example.usermanagement.domain.Role;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UUID createRole(@NotBlank String roleName) {
        Role role = new Role(roleName);
        return roleRepository.save(role).getId();
    }
}