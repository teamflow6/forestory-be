package com.teamflow.forestory_be.likes.infrastructure.persistence.repository;

import com.teamflow.forestory_be.likes.domain.vo.LikeStatus;
import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import com.teamflow.forestory_be.likes.infrastructure.persistence.entity.LikeJpaEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeJpaRepository extends JpaRepository<LikeJpaEntity, Long> {
    Optional<LikeJpaEntity> findByUserIdAndTargetTypeAndTargetId(Long userId, TargetType type, Long targetId);

    @Query("""
        SELECT l FROM LikeJpaEntity l
        WHERE l.userId = :userId
          AND l.targetType = :targetType
          AND l.targetId = :targetId
          AND l.status <> 'DELETED'
    """)
    Optional<LikeJpaEntity> findActiveByUserAndTarget(@Param("userId") Long userId,
                                                      @Param("targetType") TargetType targetType,
                                                      @Param("targetId") Long targetId);

    @Query("""
        SELECT l FROM LikeJpaEntity l
        WHERE l.userId = :userId
          AND l.status = 'ACTIVE'
        ORDER BY l.updatedAt DESC, l.id DESC
    """)
    List<LikeJpaEntity> firstPage(@Param("userId") Long userId, Pageable pageable);

    @Query("""
        SELECT l FROM LikeJpaEntity l
        WHERE l.userId = :userId
          AND l.status = 'ACTIVE'
          AND ( l.updatedAt < :cursorUpdatedAt
             OR (l.updatedAt = :cursorUpdatedAt AND l.id < :cursorLikeId) )
        ORDER BY l.updatedAt DESC, l.id DESC
    """)
    List<LikeJpaEntity> nextPage(@Param("userId") Long userId,
                                 @Param("cursorUpdatedAt") LocalDateTime cursorUpdatedAt,
                                 @Param("cursorLikeId") Long cursorLikeId,
                                 Pageable pageable);



    @Modifying
    @Query("""
        DELETE FROM LikeJpaEntity l
         WHERE l.userId = :userId
           AND l.targetType = :targetType
           AND l.targetId IN :targetIds
    """)
    void deleteAllByUserIdAndTargetIdsAndTargetType(
            @Param("userId") Long userId,
            @Param("targetIds") List<Long> targetIds,
            @Param("targetType") TargetType targetType
    );
}