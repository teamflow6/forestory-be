package com.teamflow.forestory_be.likes.domain.entity;

import com.teamflow.forestory_be.likes.domain.vo.LikeStatus;
import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Likes {

    private final Long id;
    private final Long userId;        // 누가
    private final TargetType target;  // 무엇에 대한 좋아요인지
    private final Long targetId;
    private final LikeStatus status;  // ACTIVE / DELETED

    private Likes(Long id, Long userId, TargetType target, Long targetId, LikeStatus status) {
        this.id = Objects.requireNonNull(id);
        this.userId = Objects.requireNonNull(userId);
        this.target = Objects.requireNonNull(target);
        this.targetId = Objects.requireNonNull(targetId);
        this.status = Objects.requireNonNull(status);
    }

    /** 좋아요 생성 (ACTIVE) */
    public static Likes create(Long userId, TargetType target, Long targetId) {
        Long id = TsidGenerator.generate();
        return new Likes(id, userId, target, targetId, LikeStatus.ACTIVE);
    }

    /** 복원/토글 없이, '취소(삭제)' 상태로 전이만 제공 */
    public Likes delete() {
        return new Likes(this.id, this.userId, this.target, this.targetId, LikeStatus.DELETED);
    }

    /** 영속 계층에서 불러올 때 사용 */
    public static Likes reconstruct(Long id, Long userId, TargetType target, Long targetId, LikeStatus status) {
        return new Likes(id, userId, target, targetId, status);
    }

    public boolean isActive() {
        return this.status == LikeStatus.ACTIVE;
    }

    public boolean isDeleted() {
        return this.status == LikeStatus.DELETED;
    }

    public Likes activate() {
        if (this.status == LikeStatus.ACTIVE) return this;
        return new Likes(this.id, this.userId, this.target, this.targetId, LikeStatus.ACTIVE);
    }

}
