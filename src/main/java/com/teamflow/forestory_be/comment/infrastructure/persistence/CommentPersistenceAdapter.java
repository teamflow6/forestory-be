package com.teamflow.forestory_be.comment.infrastructure.persistence;

import com.teamflow.forestory_be.comment.domain.entity.Comment;
import com.teamflow.forestory_be.comment.domain.exception.CommentNotFoundException;
import com.teamflow.forestory_be.comment.domain.repository.CommentRepositoryPort;
import com.teamflow.forestory_be.comment.infrastructure.persistence.entity.CommentJpaEntity;
import com.teamflow.forestory_be.comment.infrastructure.persistence.repository.CommentJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements CommentRepositoryPort {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Long save(Comment comment) {
        CommentJpaEntity commentJpaEntity = CommentPersistenceMapper.toJpaEntity(comment);
        return commentJpaRepository.save(commentJpaEntity).getId();
    }

    @Override
    public Comment findById(Long id) {
        CommentJpaEntity commentJpaEntity = commentJpaRepository.findById(id)
            .orElseThrow(CommentNotFoundException::new);
        return CommentPersistenceMapper.toDomainEntity(commentJpaEntity);
    }

    @Override
    public List<Comment> findAllRootByPostId(Long postId) {
        return commentJpaRepository.findAllRootByPostId(postId).stream()
            .map(CommentPersistenceMapper::toDomainEntity)
            .toList();
    }

    @Override
    public List<Comment> findAllReplyCommentByCommentId(Long commentId) {
        return commentJpaRepository.findAllReplyByCommentId(commentId).stream()
            .map(CommentPersistenceMapper::toDomainEntity)
            .toList();
    }

    @Override
    public List<Comment> findAllRootByUserId(Long userId) {
        return commentJpaRepository.findAllByUserId(userId).stream()
            .map(CommentPersistenceMapper::toDomainEntity)
            .toList();
    }
}
