package com.teamflow.forestory_be.story.series.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateSeriesRequest(

    @NotBlank(message = "제목은 필수입니다.")
    String title,

    @NotBlank(message = "소개는 필수입니다.")
    String introduction,

    String thumbnailUrl,

    @NotNull
    String type,

    @NotBlank
    String seriesStatus

    ){
}
