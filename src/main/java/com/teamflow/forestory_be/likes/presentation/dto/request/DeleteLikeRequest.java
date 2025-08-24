package com.teamflow.forestory_be.likes.presentation.dto.request;

import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "좋아요 취소 요청 DTO")
public record DeleteLikeRequest(

        @Schema(
                description = "좋아요 대상 타입",
                example = "ARTICLE",
                allowableValues = {"ARTICLE", "ESSAY", "NOVEL"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "대상 타입은 필수입니다.")
        TargetType targetType,

        @Schema(
                description = "좋아요 대상 ID",
                example = "123",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "대상 ID는 필수입니다.")
        Long targetId
) {}
