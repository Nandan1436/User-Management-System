package com.example.usermanagement.application;

import com.example.usermanagement.application.interfaces.RoleRepository;
import com.example.usermanagement.application.interfaces.UserRepository;
import com.example.usermanagement.domain.Role;
import com.example.usermanagement.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_success() {
        String name = "John Doe";
        String email = "john@example.com";
        User user = new User(name, email);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UUID userId = userService.createUser(name, email);

        assertNotNull(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserDetails_success() {
        UUID userId = UUID.randomUUID();
        User user = new User("John Doe", "john@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserDetails(userId);

        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserDetails_userNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserDetails(userId);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void assignRoleToUser_success() {
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        User user = new User("John Doe", "john@example.com");
        Role role = new Role("ADMIN");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.assignRoleToUser(userId, roleId);

        verify(userRepository, times(1)).findById(userId);
        verify(roleRepository, times(1)).findById(roleId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void assignRoleToUser_userNotFound() {
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.assignRoleToUser(userId, roleId);
        });

        assertEquals("User not found", exception.getMessage());
    }
}