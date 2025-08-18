package com.teamflow.forestory_be.story.series.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetNextChapterInfoResponse(

        @Schema(description = "시리즈 ID")
        String seriesId,

        @Schema(description = "시리즈 제목")
        String seriesTitle,

        @Schema(description = "다음 회차 번호")
        Integer nextChapterNumber
) {
    public static GetNextChapterInfoResponse of(String seriesId, String seriesTitle, Integer nextChapterNumber) {
        return new GetNextChapterInfoResponse(seriesId, seriesTitle, nextChapterNumber);
    }
}
