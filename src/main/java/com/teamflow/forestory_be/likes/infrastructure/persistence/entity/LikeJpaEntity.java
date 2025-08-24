package com.teamflow.forestory_be.likes.infrastructure.persistence.entity;

import com.teamflow.forestory_be.likes.domain.vo.LikeStatus;
import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import com.teamflow.forestory_be.support.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@Table(
        name = "likes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_like_unique",
                        columnNames = {"user_id", "target_type", "target_id"}
                )
        },
        indexes = {
                @Index(name = "idx_like_target", columnList = "target_type, target_id")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeJpaEntity extends BaseTimeEntity {

    @Id
    @Column(name = "like_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, length = 30)
    private TargetType targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 16)
    private LikeStatus status;
}
