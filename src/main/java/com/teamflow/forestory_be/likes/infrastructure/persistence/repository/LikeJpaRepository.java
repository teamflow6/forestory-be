package com.teamflow.forestory_be.likes.infrastructure.persistence.repository;

import com.teamflow.forestory_be.likes.domain.vo.LikeStatus;
import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import com.teamflow.forestory_be.likes.infrastructure.persistence.entity.LikeJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeJpaRepository extends JpaRepository<LikeJpaEntity, Long> {
    Optional<LikeJpaEntity> findByUserIdAndTargetTypeAndTargetId(Long userId, TargetType type, Long targetId);
}