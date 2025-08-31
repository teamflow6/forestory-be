package com.teamflow.forestory_be.comment.domain.entity;

import com.teamflow.forestory_be.comment.domain.exception.InvalidCommentStatusException;
import com.teamflow.forestory_be.comment.domain.exception.InvalidCommentWriterException;
import com.teamflow.forestory_be.comment.domain.exception.UnMatchedCommentException;
import com.teamflow.forestory_be.comment.domain.vo.CommentContent;
import com.teamflow.forestory_be.comment.domain.vo.CommentStatus;
import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;

// TODO: View Model 분리
@Getter
public class Comment {

    private final Long id;
    private final Long userId;
    private final Long postId;
    private final Long parentCommentId;
    private final CommentContent commentContent;
    private final Integer replyCount;
    private final CommentStatus status;
    private final LocalDate createdAt;

    private Comment(
        Long id,
        Long userId,
        Long postId,
        Long parentCommentId,
        CommentContent commentContent,
        int replyCount,
        CommentStatus status
    ) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.parentCommentId = parentCommentId;
        this.commentContent = commentContent;
        this.replyCount = replyCount;
        this.status = status;
        this.createdAt = LocalDate.now();
    }

    public Comment update(
        Long userId,
        CommentContent commentContent
    ) {
        validateStatus(CommentStatus.ACTIVE);
        validateUser(userId);
        validatePost(postId);
        return new Comment(
            id,
            userId,
            postId,
            parentCommentId,
            commentContent,
            replyCount,
            status
        );
    }

    public Comment delete(
        Long userId,
        Long postId
    ) {
        validateStatus(CommentStatus.ACTIVE);
        validateUser(userId);
        validatePost(postId);
        return new Comment(
            id,
            this.userId,
            this.postId,
            parentCommentId,
            commentContent,
            replyCount,
            CommentStatus.INACTIVE
        );
    }

    public Comment increaseReplyCount() {
        if (isReply()) {
            return this;
        }
        return new Comment(
            id,
            userId,
            postId,
            parentCommentId,
            commentContent,
            replyCount + 1,
            status
        );
    }

    public Comment decreaseReplyCount() {
        if (isReply()) {
            return this;
        }
        return new Comment(
            id,
            userId,
            postId,
            parentCommentId,
            commentContent,
            replyCount - 1,
            status
        );
    }

    public static Comment create(
        Long userId,
        Long postId,
        Long parentCommentId,
        CommentContent content
    ) {
        if (parentCommentId == null) {
            return createRoot(userId, postId, content);
        }
        return createReply(userId, postId, parentCommentId, content);
    }

    private static Comment createRoot(
        Long userId,
        Long postId,
        CommentContent content
    ) {
        Long id = TsidGenerator.generate();
        return new Comment(
            id,
            userId,
            postId,
            null,
            content,
            0,
            CommentStatus.ACTIVE
        );
    }

    private static Comment createReply(
        Long userId,
        Long postId,
        Long parentCommentId,
        CommentContent content
    ) {
        Long id = TsidGenerator.generate();
        return new Comment(
            id,
            userId,
            postId,
            parentCommentId,
            content,
            0,
            CommentStatus.ACTIVE
        );
    }

    public static Comment reconstruct(
        Long id,
        Long userId,
        Long postId,
        Long parentCommentId,
        CommentContent content,
        int replyCount,
        CommentStatus status
    ) {
        return new Comment(
            id,
            userId,
            postId,
            parentCommentId,
            content,
            replyCount,
            status
        );
    }

    public boolean isReply() {
        return parentCommentId != null;
    }

    private void validateUser(Long userId) {
        if (!Objects.equals(this.userId, userId)) {
            throw new InvalidCommentWriterException();
        }
    }

    private void validatePost(Long postId) {
        if (!Objects.equals(this.postId, postId)) {
            throw new UnMatchedCommentException();
        }
    }

    private void validateStatus(CommentStatus status) {
        if (this.status != status) {
            throw new InvalidCommentStatusException();
        }
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + id +
            ", userId=" + userId +
            ", postId=" + postId +
            ", parentCommentId=" + parentCommentId +
            ", commentContent=" + commentContent +
            ", replyCount=" + replyCount +
            ", status=" + status +
            ", createdAt=" + createdAt +
            '}';
    }
}
