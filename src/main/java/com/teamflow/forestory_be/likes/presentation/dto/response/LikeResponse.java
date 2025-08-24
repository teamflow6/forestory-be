package com.teamflow.forestory_be.likes.presentation.dto.response;

import com.teamflow.forestory_be.likes.domain.entity.Likes;
import com.teamflow.forestory_be.likes.domain.vo.LikeStatus;
import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "좋아요 응답 DTO")
public record LikeResponse(

        @Schema(
                description = "좋아요 ID",
                example = "101",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String id,

        @Schema(
                description = "사용자 ID",
                example = "15",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String userId,

        @Schema(
                description = "좋아요 대상 타입",
                example = "POST",
                allowableValues = {"POST", "COMMENT", "CHAPTER", "REVIEW"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        TargetType targetType,

        @Schema(
                description = "좋아요 대상 ID",
                example = "123",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String targetId,

        @Schema(
                description = "좋아요 상태",
                example = "ACTIVE",
                allowableValues = {"ACTIVE", "DELETED"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        LikeStatus status
) {
        public static LikeResponse from(Likes like) {
                return new LikeResponse(
                        like.getId().toString(),
                        like.getUserId().toString(),
                        like.getTarget(),
                        like.getTargetId().toString(),
                        like.getStatus()
                );
        }
}
