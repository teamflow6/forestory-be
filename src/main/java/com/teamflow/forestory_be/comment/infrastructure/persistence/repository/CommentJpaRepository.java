package com.teamflow.forestory_be.comment.infrastructure.persistence.repository;

import com.teamflow.forestory_be.comment.infrastructure.persistence.entity.CommentJpaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentJpaRepository extends JpaRepository<CommentJpaEntity, Long> {

    @Query("""
        SELECT c FROM CommentJpaEntity c
                WHERE c.postId = :postId
                AND c.parentCommentId IS NULL
                AND c.status = 'ACTIVE'
        """)
    List<CommentJpaEntity> findAllRootByPostId(Long postId);

    @Query("""
        SELECT c FROM CommentJpaEntity c
                WHERE c.parentCommentId = :parentCommentId
                AND c.status = 'ACTIVE'
        """)
    List<CommentJpaEntity> findAllReplyByCommentId(Long parentCommentId);

    @Query("""
        SELECT c FROM CommentJpaEntity c
                WHERE c.userId = :userId
                AND c.status = 'ACTIVE'
        """)
    List<CommentJpaEntity> findAllByUserId(Long userId);
}
