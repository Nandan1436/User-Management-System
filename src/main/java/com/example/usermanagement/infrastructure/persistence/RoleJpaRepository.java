package com.example.usermanagement.infrastructure.persistence;

import com.example.usermanagement.application.interfaces.RoleRepository;
import com.example.usermanagement.domain.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RoleJpaRepository implements RoleRepository {
    private final RoleJpaRepositorySpringData roleJpaRepositorySpringData;

    public RoleJpaRepository(RoleJpaRepositorySpringData roleJpaRepositorySpringData) {
        this.roleJpaRepositorySpringData = roleJpaRepositorySpringData;
    }

    @Override
    public Role save(Role role) {
        RoleJpaEntity entity = new RoleJpaEntity(role.getId(), role.getRoleName());
        roleJpaRepositorySpringData.save(entity);
        return role;
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return roleJpaRepositorySpringData.findById(id)
                .map(entity -> new Role(entity.getRoleName()));
    }
}