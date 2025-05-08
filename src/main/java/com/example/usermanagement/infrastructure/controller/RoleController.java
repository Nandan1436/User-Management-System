package com.example.usermanagement.infrastructure.controller;

import com.example.usermanagement.application.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @Operation(summary = "Create a new role", description = "Creates a role with the provided role name.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Role created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<String> createRole(@Valid @RequestBody CreateRoleRequest request) {
        UUID roleId = roleService.createRole(request.getRoleName());
        return new ResponseEntity<>("Created Role " + roleId, HttpStatus.CREATED);
    }
}