package com.teamflow.forestory_be.user.infrastructure.persistence;

import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.domain.vo.Name;
import com.teamflow.forestory_be.user.infrastructure.persistence.entity.UserJpaEntity;

public class UserPersistenceMapper {

    private UserPersistenceMapper() {
    }

    public static User toDomainEntity(UserJpaEntity userJpaEntity) {
        return User.reconstruct(
            userJpaEntity.getId(),
            new Name(userJpaEntity.getName()),
            userJpaEntity.getProfileImageUrl(),
            userJpaEntity.getStatus()
        );
    }

    public static UserJpaEntity toJpaEntity(User user) {
        return UserJpaEntity.builder()
            .id(user.getId())
            .name(user.getName().value())
            .profileImageUrl(user.getProfileImageUrl())
            .status(user.getStatus())
            .build();
    }
}
