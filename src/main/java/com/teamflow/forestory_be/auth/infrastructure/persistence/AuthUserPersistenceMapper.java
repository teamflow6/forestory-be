package com.teamflow.forestory_be.auth.infrastructure.persistence;

import com.teamflow.forestory_be.auth.domain.entity.AuthUser;
import com.teamflow.forestory_be.auth.domain.vo.AuthMethod;
import com.teamflow.forestory_be.auth.domain.vo.EmailAuth;
import com.teamflow.forestory_be.auth.domain.vo.SocialAuth;
import com.teamflow.forestory_be.auth.domain.vo.SocialType;
import com.teamflow.forestory_be.auth.infrastructure.persistence.entity.AuthUserJpaEntity;

public final class AuthUserPersistenceMapper {

    private AuthUserPersistenceMapper() {
    }

    public static AuthUser toDomainEntity(AuthUserJpaEntity authUserJpaEntity) {
        if (authUserJpaEntity.getSocialType() == SocialType.EMAIL) {
            return AuthUser.createEmail(
                authUserJpaEntity.getUserId(),
                authUserJpaEntity.getEmail(),
                authUserJpaEntity.getFirebaseUid()
            );
        }
        return AuthUser.createSocial(
            authUserJpaEntity.getUserId(),
            authUserJpaEntity.getSocialId(),
            authUserJpaEntity.getSocialType()
        );
    }

    public static AuthUserJpaEntity toJpaEntity(AuthUser authUser) {
        AuthMethod authMethod = authUser.getAuthMethod();
        if (authMethod instanceof EmailAuth emailAuth) {
            return AuthUserJpaEntity.builder()
                .id(authUser.getId())
                .userId(authUser.getUserId())
                .email(emailAuth.email())
                .firebaseUid(emailAuth.firebaseUid())
                .build();
        }
        SocialAuth socialAuth = (SocialAuth) authMethod;
        return AuthUserJpaEntity.builder()
            .id(authUser.getId())
            .userId(authUser.getUserId())
            .socialId(socialAuth.socialId())
            .socialType(socialAuth.socialType())
            .build();
    }
}
