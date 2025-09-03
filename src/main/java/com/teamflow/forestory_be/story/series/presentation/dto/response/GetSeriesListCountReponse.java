package com.teamflow.forestory_be.story.series.presentation.dto.response;

public record GetSeriesListCountReponse(
        int count
) {
    public static GetSeriesListCountReponse of(int count){
        return new GetSeriesListCountReponse(count);
    }
}
