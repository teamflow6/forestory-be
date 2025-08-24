package com.teamflow.forestory_be.likes.application.service;

import com.teamflow.forestory_be.likes.application.dto.command.CreateLikeCommand;
import com.teamflow.forestory_be.likes.application.dto.command.DeleteLikeCommand;
import com.teamflow.forestory_be.likes.domain.entity.Likes;
import com.teamflow.forestory_be.likes.domain.repository.LikeCounterPort;
import com.teamflow.forestory_be.likes.domain.repository.LikeRepositoryPort;
import com.teamflow.forestory_be.likes.domain.vo.LikeStatus;
import com.teamflow.forestory_be.likes.presentation.dto.response.LikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepositoryPort likeRepositoryPort;
    private final LikeCounterPort likeCounterPort;

    @Transactional
    public LikeResponse create(CreateLikeCommand command) {
        Likes like = likeRepositoryPort
                .findByUserAndTarget(command.userId(), command.targetType(), command.targetId())
                .orElse(null);

        if (like == null) {
            Likes saved = likeRepositoryPort.save(
                    Likes.create(command.userId(), command.targetType(), command.targetId()) // ACTIVE 로 생성
            );
            likeCounterPort.increase(command.targetType(), command.targetId());
            return LikeResponse.from(saved);
        }

        if (like.isActive()) {            // 이미 ACTIVE면 멱등 처리
            return LikeResponse.from(like);
        }

        like.activate();                  // DELETED -> ACTIVE
        Likes saved = likeRepositoryPort.save(like);
        likeCounterPort.increase(command.targetType(), command.targetId()); // 상태가 바뀔 때만 증가
        return LikeResponse.from(saved);
    }

    @Transactional
    public LikeResponse delete(DeleteLikeCommand command) {
        Likes like = likeRepositoryPort
                .findByUserAndTarget(command.userId(), command.targetType(), command.targetId())
                .orElse(null);

        if (like == null || like.isDeleted()) {   // 없거나 이미 DELETED면 멱등
            return new LikeResponse(
                    null,
                    command.userId().toString(),
                    command.targetType(),
                    command.targetId().toString(),
                    LikeStatus.DELETED
            );
        }

        like.delete();                    // ACTIVE -> DELETED
        Likes saved = likeRepositoryPort.save(like);
        likeCounterPort.decrease(command.targetType(), command.targetId()); // 상태가 바뀔 때만 감소
        return LikeResponse.from(saved);
    }

}
