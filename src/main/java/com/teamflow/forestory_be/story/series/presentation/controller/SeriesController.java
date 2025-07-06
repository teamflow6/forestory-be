package com.teamflow.forestory_be.story.series.presentation.controller;

import com.teamflow.forestory_be.article.presentation.dto.request.UpdateArticleRequest;
import com.teamflow.forestory_be.story.series.application.dto.command.CreateSeriesCommand;
import com.teamflow.forestory_be.story.series.application.dto.command.UpdateSeriesCommand;
import com.teamflow.forestory_be.story.series.application.dto.query.GetSeriesQuery;
import com.teamflow.forestory_be.story.series.application.service.SeriesService;
import com.teamflow.forestory_be.story.series.domain.entity.Series;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesStatus;
import com.teamflow.forestory_be.story.series.domain.vo.Type;
import com.teamflow.forestory_be.story.series.presentation.dto.request.CreateSeriesRequest;
import com.teamflow.forestory_be.story.series.presentation.dto.request.UpdateSeriesRequest;
import com.teamflow.forestory_be.story.series.presentation.dto.response.CreateSeriesResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.GetSeriesResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.UpdateSeriesResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/{seriesId}")
    public ResponseEntity<GetSeriesResponse> getSeries(
            @AuthenticationPrincipal Long userId,
            @PathVariable @NotNull Long seriesId,
            @RequestParam(defaultValue = "asc") String sort, // asc: 첫화부터, desc: 최신순
            @RequestParam(required = false) Integer lastChapterNumber,
            @RequestParam(defaultValue = "5") int size
    ) {
        GetSeriesQuery query = new GetSeriesQuery(seriesId, sort, lastChapterNumber, size);
        GetSeriesResponse getSeriesResponse = seriesService.getSeries(query);
        return ResponseEntity.ok(getSeriesResponse);
    }

    @PutMapping("/{seriesId}")
    public ResponseEntity<UpdateSeriesResponse> updateSeries(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid UpdateSeriesRequest request,
            @PathVariable @NotNull Long seriesId
    ){
        UpdateSeriesCommand updateSeriesCommand = new UpdateSeriesCommand(
                seriesId,
                userId,
                request.title(),
                request.introduction(),
                request.thumbnailUrl(),
                Type.from(request.type()),
                SeriesStatus.from(request.seriesStatus())
        );
        UpdateSeriesResponse updateSeriesResponse = seriesService.update(updateSeriesCommand);
        return ResponseEntity.ok(updateSeriesResponse);

    }

}
