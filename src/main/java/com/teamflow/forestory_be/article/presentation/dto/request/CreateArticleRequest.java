package com.teamflow.forestory_be.article.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "아티클 생성 요청 DTO")
public record CreateArticleRequest(

        @Schema(
                description = "아티클 제목",
                example = "나만의 글쓰기 노하우",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @Schema(
                description = "아티클 부제목",
                example = "글을 잘 쓰는 3가지 습관",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "부제목은 필수입니다.")
        String subtitle,

        @Schema(
                description = "아티클 본문 내용",
                example = "매일 글을 쓰는 습관을 기르면 자연스럽게 실력이 향상됩니다...",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "본문은 필수입니다.")
        String content,

        @Schema(
                description = "썸네일 이미지 URL",
                example = "https://cdn.image.com/sample-thumbnail.png"
        )
        String thumbnailUrl,

        @Schema(
                description = "게시 상태 (DRAFT 또는 PUBLISHED)",
                example = "DRAFT",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull
        String status
) {
}
