package com.teamflow.forestory_be.likes.domain.repository;

import com.teamflow.forestory_be.likes.domain.entity.Likes;
import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import java.util.Optional;

public interface LikeRepositoryPort {
    Likes save(Likes like);
    Optional<Likes> findByUserAndTarget(Long userId, TargetType targetType, Long targetId);
}

