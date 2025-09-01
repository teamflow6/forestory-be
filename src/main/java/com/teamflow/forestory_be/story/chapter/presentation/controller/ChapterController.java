package com.teamflow.forestory_be.story.chapter.presentation.controller;

import com.teamflow.forestory_be.story.chapter.application.dto.command.CreateChapterCommand;
import com.teamflow.forestory_be.story.chapter.application.dto.command.DeleteChapterCommand;
import com.teamflow.forestory_be.story.chapter.application.dto.command.UpdateChapterCommand;
import com.teamflow.forestory_be.story.chapter.application.dto.query.GetChapterQuery;
import com.teamflow.forestory_be.story.chapter.application.service.ChapterService;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterStatus;
import com.teamflow.forestory_be.story.chapter.presentation.dto.request.CreateChapterRequest;
import com.teamflow.forestory_be.story.chapter.presentation.dto.request.UpdateChapterRequest;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.CreateChapterResponse;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.DeleteChapterResponse;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.GetChapterResponse;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.UpdateChapterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chapters")
@RequiredArgsConstructor
@Validated
public class ChapterController {

    private final ChapterService chapterService;

    @PostMapping
    @Operation(summary = "챕터 등록", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<CreateChapterResponse> createChapter(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @RequestBody @Valid CreateChapterRequest request
    ) {
        CreateChapterCommand command = new CreateChapterCommand(
                userId,
                request.seriesId(),
                request.chapterTitle(),
                request.chapterSubtitle(),
                request.chapterBody(),
                request.thumbnailUrl(),
                ChapterStatus.from(request.status())
        );
        CreateChapterResponse response = chapterService.createChapter(command);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/{chapterId}")
    @Operation(summary = "챕터 조회", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<GetChapterResponse> getChapter(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @PathVariable Long chapterId
    ) {

        GetChapterQuery query = new GetChapterQuery(chapterId, userId);
        GetChapterResponse response = chapterService.getChapter(query);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{chapterId}")
    @Operation(summary = "챕터 수정", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<UpdateChapterResponse> updateChapter(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @NotNull @PathVariable Long chapterId,
            @RequestBody @Valid UpdateChapterRequest request
    ) {
        UpdateChapterCommand command = new UpdateChapterCommand(
                chapterId,
                request.seriesId(),
                userId,
                request.chapterTitle(),
                request.chapterSubtitle(),
                request.chapterBody(),
                request.thumbnailUrl(),
                ChapterStatus.from(request.status()),
                request.chapterNumber()
        );

        UpdateChapterResponse response = chapterService.updateChapter(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{chapterId}")
    @Operation(summary = "챕터 삭제", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<DeleteChapterResponse> deleteChapter(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @PathVariable Long chapterId
    ) {
        DeleteChapterCommand command = new DeleteChapterCommand(
                chapterId,
                userId);


        DeleteChapterResponse response = chapterService.deleteChapter(command);
        return ResponseEntity.ok(response);
    }




}
