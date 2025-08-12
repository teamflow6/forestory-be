package com.teamflow.forestory_be.story.series.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "시리즈 생성 요청 DTO")
public record CreateSeriesRequest(

        @Schema(
                description = "시리즈 제목",
                example = "팀플로우 작가 플랫폼 시리즈",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "제목은 필수입니다.")
        String seriesTitle,

        @Schema(
                description = "작품 소개",
                example = "안녕하세요. 이 시리즈는 개발과 글쓰기를 함께 다룹니다.",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "작품 소개는 필수입니다.")
        String introduction,

        @Schema(
                description = "썸네일 이미지 URL (선택)",
                example = "https://cdn.example.com/images/series-thumb.png"
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
                example = "DRAFT",
                allowableValues = {"DRAFT", "PUBLISHED"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "상태는 필수입니다.")
        String status
) {}
