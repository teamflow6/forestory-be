package com.teamflow.forestory_be.story.series.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "시리즈 수정 요청 DTO")
public record UpdateSeriesRequest(

        @Schema(
                description = "수정할 시리즈 제목",
                example = "팀플로우 작가 플랫폼 시리즈 - 리뉴얼",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @Schema(
                description = "수정할 소개",
                example = "시리즈 소개를 최신 내용으로 업데이트합니다.",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "소개는 필수입니다.")
        String introduction,

        @Schema(
                description = "썸네일 이미지 URL (선택)",
                example = "https://cdn.example.com/images/series-thumb-v2.png"
        )
        String thumbnailUrl,

        @Schema(
                description = "작품 타입 (Type enum)",
                example = "NOVEL",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "타입은 필수입니다.")
        String type,

        @Schema(
                description = "시리즈 상태 (SeriesStatus enum)",
                example = "PUBLISHED",
                allowableValues = {"DRAFT", "PUBLISHED"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "상태는 필수입니다.")
        String seriesStatus
) {}
