package com.teamflow.forestory_be.likes.domain.repository;

import com.teamflow.forestory_be.likes.domain.entity.Likes;
import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LikeRepositoryPort {
    Likes save(Likes like);
    Optional<Likes> findByUserAndTarget(Long userId, TargetType targetType, Long targetId);

    List<Likes> sliceByUserUpdatedDesc(Long userId, LocalDateTime cursorUpdatedAt, Long cursorLikeId, int limitPlusOne);

    void deleteAllByUserIdAndTargetIdsAndTargetType(Long userId, List<Long> targetIds, TargetType targetType);
}

