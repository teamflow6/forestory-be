package com.teamflow.forestory_be.user.infrastructure.persistence;

import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.domain.vo.ContactUrl;
import com.teamflow.forestory_be.user.domain.vo.Introduction;
import com.teamflow.forestory_be.user.domain.vo.Name;
import com.teamflow.forestory_be.user.domain.vo.ProfileImageUrl;
import com.teamflow.forestory_be.user.infrastructure.persistence.entity.UserJpaEntity;

public class UserPersistenceMapper {

    private UserPersistenceMapper() {
    }

    public static User toDomainEntity(UserJpaEntity userJpaEntity) {
        return User.reconstruct(
            userJpaEntity.getId(),
            new Name(userJpaEntity.getName()),
            new ProfileImageUrl(userJpaEntity.getProfileImageUrl()),
            userJpaEntity.getStatus(),
            userJpaEntity.getIntroduction() != null ? new Introduction(userJpaEntity.getIntroduction()) : null,
            new ContactUrl(userJpaEntity.getContactUrl())
        );
    }


    public static UserJpaEntity toJpaEntity(User user) {
        return UserJpaEntity.builder()
            .id(user.getId())
            .name(user.getName().value())
            .profileImageUrl(user.getProfileImageUrl().value())
            .status(user.getStatus())
            .introduction(user.getIntroduction().value())
            .contactUrl(user.getContactUrl().value())
            .build();
    }
}
