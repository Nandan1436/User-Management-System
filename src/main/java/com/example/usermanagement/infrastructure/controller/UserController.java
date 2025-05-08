package com.example.usermanagement.infrastructure.controller;

import com.example.usermanagement.application.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a user with the provided name and email.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequest request) {
        UUID userId = userService.createUser(request.getName(), request.getEmail());
        return new ResponseEntity<>("Created User " + userId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user details", description = "Retrieves user details including assigned roles by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User details retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> getUserDetails(@PathVariable UUID id) {
        UserResponse response = UserResponse.fromDomain(userService.getUserDetails(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/assign-role/{roleId}")
    @Operation(summary = "Assign a role to a user", description = "Assigns an existing role to an existing user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role assigned successfully"),
            @ApiResponse(responseCode = "404", description = "User or role not found")
    })
    public ResponseEntity<String> assignRoleToUser(@PathVariable UUID userId, @PathVariable UUID roleId) {
        userService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok("Role assigned successfully");
    }
}