package com.example.usermanagement.application;

import com.example.usermanagement.application.interfaces.UserRepository;
import com.example.usermanagement.application.interfaces.RoleRepository;
import com.example.usermanagement.domain.User;
import com.example.usermanagement.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UUID createUser(@NotBlank String name, @NotBlank @Email String email) {
        User user = new User(name, email);
        return userRepository.save(user).getId();
    }

    public User getUserDetails(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void assignRoleToUser(UUID userId, UUID roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        user.assignRole(role);
        userRepository.save(user);
    }
}