package com.teamflow.forestory_be.user.infrastructure.persistence;

import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.domain.exception.UserNotFoundException;
import com.teamflow.forestory_be.user.domain.repository.UserRepositoryPort;
import com.teamflow.forestory_be.user.domain.vo.Name;
import com.teamflow.forestory_be.user.infrastructure.persistence.entity.UserJpaEntity;
import com.teamflow.forestory_be.user.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Long save(User user) {
        UserJpaEntity userJpaEntity = UserPersistenceMapper.toJpaEntity(user);
        return userJpaRepository.save(userJpaEntity).getId();
    }

    @Override
    public User getById(Long userId) {
        return userJpaRepository.findById(userId)
            .map(UserPersistenceMapper::toDomainEntity)
            .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Boolean existsByName(Name name) {
        return userJpaRepository.existsByName(name.value());
    }
}
