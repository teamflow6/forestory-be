package com.teamflow.forestory_be.story.series.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateSeriesRequest(

        @NotBlank(message = "제목은 필수입니다.")
        String seriesTitle,

        @NotBlank(message = "작품 소개는 필수입니다.")
        String introduction,

        String thumbnailUrl,

        @NotBlank(message = "타입은 필수입니다.")
        String type,

        @NotBlank(message = "타입은 필수입니다.")
        String status
) {
}
