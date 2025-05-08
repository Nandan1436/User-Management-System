package com.example.usermanagement.application;

import com.example.usermanagement.application.interfaces.RoleRepository;
import com.example.usermanagement.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRole_success() {
        String roleName = "ADMIN";
        Role role = new Role(roleName);

        when(roleRepository.save(any(Role.class))).thenReturn(role);

        UUID roleId = roleService.createRole(roleName);

        assertNotNull(roleId);
        verify(roleRepository, times(1)).save(any(Role.class));
    }
}