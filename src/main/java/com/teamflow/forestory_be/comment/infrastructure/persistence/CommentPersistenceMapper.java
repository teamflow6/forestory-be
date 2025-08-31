package com.teamflow.forestory_be.comment.infrastructure.persistence;

import com.teamflow.forestory_be.comment.domain.entity.Comment;
import com.teamflow.forestory_be.comment.domain.vo.CommentContent;
import com.teamflow.forestory_be.comment.infrastructure.persistence.entity.CommentJpaEntity;

public class CommentPersistenceMapper {

    private CommentPersistenceMapper() {
    }

    public static Comment toDomainEntity(CommentJpaEntity commentJpaEntity) {
        return Comment.reconstruct(
            commentJpaEntity.getId(),
            commentJpaEntity.getUserId(),
            commentJpaEntity.getPostId(),
            commentJpaEntity.getParentCommentId(),
            new CommentContent(commentJpaEntity.getContent()),
            commentJpaEntity.getReplyCount(),
            commentJpaEntity.getStatus()
        );
    }

    public static CommentJpaEntity toJpaEntity(Comment comment) {
        return CommentJpaEntity.builder()
            .id(comment.getId())
            .userId(comment.getUserId())
            .postId(comment.getPostId())
            .parentCommentId(comment.getParentCommentId())
            .content(comment.getCommentContent().value())
            .replyCount(comment.getReplyCount())
            .status(comment.getStatus())
            .build();
    }
}
