package com.teamflow.forestory_be.article.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateArticleRequest(

        @NotBlank(message = "제목은 필수입니다.")
        @Schema(description = "제목", example = "내일을 설계하는 글쓰기")
        String title,

        @NotBlank(message = "부제목은 필수입니다.")
        @Schema(description = "부제목", example = "성장과 도전의 기록")
        String subtitle,

        @NotBlank(message = "본문은 필수입니다.")
        @Schema(description = "글 내용", example = "이 글은 에세이 시리즈의 일환으로 작성되었습니다.")
        String content,

        @Schema(description = "썸네일 URL", example = "https://cdn.s3.com/image.png")
        String thumbnailUrl,

        @NotNull
        @Schema(description = "상태값 (PUBLISHED 또는 DRAFT)", example = "PUBLISHED")
        String status
) {
}

