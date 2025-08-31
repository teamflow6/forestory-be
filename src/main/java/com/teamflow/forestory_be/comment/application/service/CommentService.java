package com.teamflow.forestory_be.comment.application.service;

import com.teamflow.forestory_be.comment.application.dto.CreateCommentCommand;
import com.teamflow.forestory_be.comment.application.dto.DeleteCommentCommand;
import com.teamflow.forestory_be.comment.application.dto.UpdateCommentCommand;
import com.teamflow.forestory_be.comment.domain.entity.Comment;
import com.teamflow.forestory_be.comment.domain.repository.CommentRepositoryPort;
import com.teamflow.forestory_be.comment.domain.vo.CommentContent;
import com.teamflow.forestory_be.comment.presentation.dto.response.CommentResponse;
import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.domain.repository.UserRepositoryPort;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO: 쿼리/커멘드 DTO 분리, 페이징
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepositoryPort commentRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    @Transactional
    public Long create(CreateCommentCommand command) {
        CommentContent content = new CommentContent(command.content());
        Comment comment = Comment.create(
            command.userId(),
            command.postId(),
            command.parentCommentId(),
            content
        );
        Long savedId = commentRepositoryPort.save(comment);

        if (comment.isReply()) {
            Comment parent = commentRepositoryPort.findById(comment.getParentCommentId());
            parent = parent.increaseReplyCount();
            commentRepositoryPort.save(parent);
        }
        return savedId;
    }

    @Transactional
    public CommentResponse update(UpdateCommentCommand command) {
        Comment findComment = commentRepositoryPort.findById(command.commentId());
        CommentContent content = new CommentContent(command.content());
        Comment updatedComment = findComment.update(command.userId(), content);
        commentRepositoryPort.save(updatedComment);

        User writer = userRepositoryPort.getById(command.userId());
        return CommentResponse.of(updatedComment, writer);
    }

    @Transactional
    public void delete(DeleteCommentCommand command) {
        Comment findComment = commentRepositoryPort.findById(command.commentId());
        Comment deletedComment = findComment.delete(command.userId(), command.postId());
        commentRepositoryPort.save(deletedComment);

        if (findComment.isReply()) {
            Comment parent = commentRepositoryPort.findById(findComment.getParentCommentId());
            parent = parent.decreaseReplyCount();
            commentRepositoryPort.save(parent);
        }
    }

    public List<CommentResponse> getAllRootCommentByPostId(Long postId) {
        List<Comment> comments = commentRepositoryPort.findAllRootByPostId(postId);
        return getAllCommentWithUsers(comments);
    }

    public List<CommentResponse> getAllReplyCommentByComment(Long commentId) {
        List<Comment> comments = commentRepositoryPort.findAllReplyCommentByCommentId(commentId);
        return getAllCommentWithUsers(comments);
    }

    public List<CommentResponse> getAllCommentByUserId(Long userId) {
        List<Comment> comments = commentRepositoryPort.findAllRootByUserId(userId);
        return getAllCommentWithUsers(comments);
    }

    private List<CommentResponse> getAllCommentWithUsers(List<Comment> comments) {
        Map<Long, User> userInformation = userRepositoryPort.findAllByIds(
                comments.stream()
                    .map(Comment::getUserId)
                    .distinct()
                    .toList()
            ).stream()
            .collect(Collectors.toMap(User::getId, Function.identity()));
        return comments.stream()
            .map(comment -> CommentResponse.of(
                comment,
                userInformation.get(comment.getUserId())
            ))
            .toList();
    }
}
