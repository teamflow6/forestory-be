package com.teamflow.forestory_be.story.chapter.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Schema(description = "챕터 생성 요청 DTO")
public record CreateChapterRequest(

        @Schema(
                description = "시리즈 ID",
                example = "123",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "시리즈 ID는 필수입니다.")
        Long seriesId,

        @Schema(
                description = "챕터 제목",
                example = "데이터베이스 설계 기초",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "제목은 필수입니다.")
        String chapterTitle,

        @Schema(
                description = "챕터 부제목",
                example = "정규화와 관계 설정",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "부제목은 필수입니다.")
        String chapterSubtitle,

        @Schema(
                description = "챕터 본문 내용",
                example = "정규화는 데이터 중복을 최소화하고 무결성을 높이기 위한 기법입니다...",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "본문 내용은 필수입니다.")
        String chapterBody,

        @Schema(
                description = "게시 상태 (DRAFT 또는 PUBLISHED)",
                example = "DRAFT",
                allowableValues = {"DRAFT", "PUBLISHED"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "상태는 필수입니다.")
        String status
) {}
