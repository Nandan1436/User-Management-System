package com.example.usermanagement.infrastructure.controller;

import jakarta.validation.constraints.NotBlank;

public class CreateRoleRequest {
    @NotBlank
    private String roleName;

    // Getters and setters
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}