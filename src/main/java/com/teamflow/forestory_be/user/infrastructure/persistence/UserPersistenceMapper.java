package com.teamflow.forestory_be.user.infrastructure.persistence;

import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.domain.vo.Introduction;
import com.teamflow.forestory_be.user.domain.vo.Name;
import com.teamflow.forestory_be.user.infrastructure.persistence.entity.UserJpaEntity;

public class UserPersistenceMapper {

    private UserPersistenceMapper() {
    }

    public static User toDomainEntity(UserJpaEntity e) {
        return User.reconstruct(
                e.getId(),
                new Name(e.getName()),
                e.getProfileImageUrl(),
                e.getStatus(),
                e.getIntroduction() != null ? new Introduction(e.getIntroduction()) : null
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
