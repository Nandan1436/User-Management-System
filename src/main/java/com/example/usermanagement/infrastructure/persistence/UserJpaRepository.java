package com.example.usermanagement.infrastructure.persistence;

import com.example.usermanagement.application.interfaces.UserRepository;
import com.example.usermanagement.domain.User;
import com.example.usermanagement.domain.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserJpaRepository implements UserRepository {
    private final UserJpaRepositorySpringData userJpaRepositorySpringData;
    private final RoleJpaRepositorySpringData roleJpaRepositorySpringData;

    public UserJpaRepository(UserJpaRepositorySpringData userJpaRepositorySpringData,
                             RoleJpaRepositorySpringData roleJpaRepositorySpringData) {
        this.userJpaRepositorySpringData = userJpaRepositorySpringData;
        this.roleJpaRepositorySpringData = roleJpaRepositorySpringData;
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = new UserJpaEntity(user.getId(), user.getName(), user.getEmail());
        List<RoleJpaEntity> roleEntities = user.getRoles().stream()
                .map(role -> roleJpaRepositorySpringData.findById(role.getId())
                        .orElse(new RoleJpaEntity(role.getId(), role.getRoleName())))
                .collect(Collectors.toList());
        entity.setRoles(roleEntities);
        userJpaRepositorySpringData.save(entity);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepositorySpringData.findById(id).map(this::toDomain);
    }

    private User toDomain(UserJpaEntity entity) {
        User user = new User(entity.getName(), entity.getEmail());
        entity.getRoles().forEach(roleEntity -> user.assignRole(new Role(roleEntity.getRoleName())));
        return user;
    }
}