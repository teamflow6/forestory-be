package com.teamflow.forestory_be.story.series.presentation.controller;

import com.teamflow.forestory_be.story.series.application.dto.command.CreateSeriesCommand;
import com.teamflow.forestory_be.story.series.application.service.SeriesService;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesStatus;
import com.teamflow.forestory_be.story.series.presentation.dto.request.CreateSeriesRequest;
import com.teamflow.forestory_be.story.series.presentation.dto.response.CreateSeriesResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/series")
@RequiredArgsConstructor
@Validated
public class SeriesController {

    private final SeriesService seriesService;

    @PostMapping
    public ResponseEntity<CreateSeriesResponse> createSeries(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid CreateSeriesRequest request
    ) {
        CreateSeriesCommand command = new CreateSeriesCommand(
                userId,
                request.seriesTitle(),
                request.introduction(),
                request.thumbnailUrl(),
                request.type(),
                SeriesStatus.from(request.status())
        );
        Long seriesId = seriesService.createSeries(command);
        return ResponseEntity.ok(CreateSeriesResponse.of(seriesId, request.seriesTitle()));

    }

}
