package com.teamflow.forestory_be.comment.domain.repository;

import com.teamflow.forestory_be.comment.domain.entity.Comment;
import java.util.List;

public interface CommentRepositoryPort {

    Long save(Comment comment);

    Comment findById(Long id);

    List<Comment> findAllRootByPostId(Long postId);

    List<Comment> findAllReplyCommentByCommentId(Long commentId);

    List<Comment> findAllRootByUserId(Long userId);
}
