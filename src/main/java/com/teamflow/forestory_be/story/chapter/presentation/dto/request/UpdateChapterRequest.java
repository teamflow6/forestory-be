package com.teamflow.forestory_be.story.chapter.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "챕터 수정 요청 DTO")
public record UpdateChapterRequest(

        @Schema(
                description = "시리즈 ID",
                example = "123",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "시리즈 ID는 필수입니다.")
        Long seriesId,

        @Schema(
                description = "수정할 제목",
                example = "데이터베이스 설계 심화",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @Schema(
                description = "수정할 부제목",
                example = "정규화 심화와 반정규화",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "부제목은 필수입니다.")
        String subtitle,

        @Schema(
                description = "수정할 본문 내용",
                example = "정규화는 1NF, 2NF, 3NF를 거치며...",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "본문은 필수입니다.")
        String body,

        @Schema(
                description = "수정할 썸네일 URL",
                example = "https://cdn.example.com/thumbs/ch-3.png",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String thumbnailUrl,

        @Schema(
                description = "게시 상태 (DRAFT 또는 PUBLISHED)",
                example = "PUBLISHED",
                allowableValues = {"DRAFT", "PUBLISHED"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "상태는 필수입니다.")
        String status,

        @Schema(
                description = "회차 번호(1 이상 정수)",
                example = "3",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "회차 번호는 필수입니다.")
        @Min(value = 1, message = "회차 번호는 1 이상이어야 합니다.")
        Integer chapterNumber
) {}
