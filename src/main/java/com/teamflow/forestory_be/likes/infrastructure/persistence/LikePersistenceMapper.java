package com.teamflow.forestory_be.likes.infrastructure.persistence;

import com.teamflow.forestory_be.likes.domain.entity.Likes;
import com.teamflow.forestory_be.likes.infrastructure.persistence.entity.LikeJpaEntity;

public final class LikePersistenceMapper {
    private LikePersistenceMapper() {}

    public static Likes toDomainEntity(LikeJpaEntity e) {
        return Likes.reconstruct(
                e.getId(),
                e.getUserId(),
                e.getTargetType(),
                e.getTargetId(),
                e.getStatus()
        );
    }

    public static LikeJpaEntity toJpaEntity(Likes like) {
        return LikeJpaEntity.builder()
                .id(like.getId())
                .userId(like.getUserId())
                .targetType(like.getTarget())
                .targetId(like.getTargetId())
                .status(like.getStatus())
                .build();
    }
}