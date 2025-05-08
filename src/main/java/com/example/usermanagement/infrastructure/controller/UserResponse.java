package com.example.usermanagement.infrastructure.controller;

import com.example.usermanagement.domain.User;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private List<String> roles;

    public static UserResponse fromDomain(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRoles(user.getRoles().stream()
                .map(role -> role.getRoleName())
                .collect(Collectors.toList()));
        return response;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}