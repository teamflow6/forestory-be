package com.teamflow.forestory_be.story.series.presentation.controller;

import com.teamflow.forestory_be.story.series.application.dto.command.CompleteSeriesCommand;
import com.teamflow.forestory_be.story.series.application.dto.command.CreateSeriesCommand;
import com.teamflow.forestory_be.story.series.application.dto.command.DeleteLatestChapterCommand;
import com.teamflow.forestory_be.story.series.application.dto.command.DeleteSeriesCommand;
import com.teamflow.forestory_be.story.series.application.dto.command.UpdateSeriesCommand;
import com.teamflow.forestory_be.story.series.application.dto.query.GetSeriesQuery;
import com.teamflow.forestory_be.story.series.application.service.SeriesService;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesStatus;
import com.teamflow.forestory_be.story.series.domain.vo.Type;
import com.teamflow.forestory_be.story.series.presentation.dto.request.CreateSeriesRequest;
import com.teamflow.forestory_be.story.series.presentation.dto.request.UpdateSeriesRequest;
import com.teamflow.forestory_be.story.series.presentation.dto.response.CompleteSeriesResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.CreateSeriesResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.DeleteChapterResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.DeleteSeriesResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.GetSeriesResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.UpdateSeriesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/series")
@RequiredArgsConstructor
@Validated
public class SeriesController {

    private final SeriesService seriesService;

    @PostMapping
    @Operation(summary = "시리즈 등록", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<CreateSeriesResponse> createSeries(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
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
    @Operation(
            summary = "시리즈 조회",
            description = "정렬은 asc(첫 화부터) / desc(최신순). lastChapterNumber로 무한스크롤 페이징 지원.",
            security = @SecurityRequirement(name = "AccessToken")
    )
    public ResponseEntity<GetSeriesResponse> getSeries(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @PathVariable @NotNull Long seriesId,
            @Parameter(description = "asc: 첫 화부터, desc: 최신순") @RequestParam(defaultValue = "asc") String sort,
            @Parameter(description = "마지막으로 본 챕터 번호(무한스크롤 기준점)") @RequestParam(required = false) Integer lastChapterNumber,
            @Parameter(description = "가져올 개수") @RequestParam(defaultValue = "5") int size
    ) {
        GetSeriesQuery query = new GetSeriesQuery(seriesId, sort, lastChapterNumber, size);
        GetSeriesResponse getSeriesResponse = seriesService.getSeries(query);
        return ResponseEntity.ok(getSeriesResponse);
    }

    @PutMapping("/{seriesId}")
    @Operation(summary = "시리즈 수정", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<UpdateSeriesResponse> updateSeries(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @RequestBody @Valid UpdateSeriesRequest request,
            @PathVariable @NotNull Long seriesId
    ) {
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

    @PutMapping("/{seriesId}/complete")
    @Operation(summary = "시리즈 연재 완료 처리", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<CompleteSeriesResponse> completeSeries(
            @AuthenticationPrincipal Long userId,
            @PathVariable @NotNull Long seriesId
    ) {
        CompleteSeriesCommand command = new CompleteSeriesCommand(seriesId, userId);
        CompleteSeriesResponse response = seriesService.completeSeries(command);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{seriesId}")
    @Operation(summary = "시리즈 삭제", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<DeleteSeriesResponse> deleteSeries(
            @AuthenticationPrincipal Long userId,
            @PathVariable @NotNull Long seriesId
    ) {
        DeleteSeriesCommand deleteSeriesCommand = new DeleteSeriesCommand(
                seriesId,
                userId
        );
        DeleteSeriesResponse deleteSeriesResponse = seriesService.delete(deleteSeriesCommand);
        return ResponseEntity.ok(deleteSeriesResponse);
    }

    @DeleteMapping("/{seriesId}/chapters/latest")
    @Operation(summary = "시리즈의 최신 챕터 삭제", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<DeleteChapterResponse> deleteLatestChapter(
            @AuthenticationPrincipal Long userId,
            @PathVariable @NotNull Long seriesId
    ) {
        DeleteLatestChapterCommand command = new DeleteLatestChapterCommand(seriesId, userId);
        DeleteChapterResponse response = seriesService.deleteLatestChapter(command);
        return ResponseEntity.ok(response);
    }

}
