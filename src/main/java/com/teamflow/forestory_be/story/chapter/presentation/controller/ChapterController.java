package com.teamflow.forestory_be.story.chapter.presentation.controller;

import com.teamflow.forestory_be.story.chapter.application.dto.command.CreateChapterCommand;
import com.teamflow.forestory_be.story.chapter.application.service.ChapterService;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterStatus;
import com.teamflow.forestory_be.story.chapter.presentation.dto.request.CreateChapterRequest;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.CreateChapterResponse;
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
@RequestMapping("api/v1/chapters")
@RequiredArgsConstructor
@Validated
public class ChapterController {

    private final ChapterService chapterService;

    @PostMapping
    public ResponseEntity<CreateChapterResponse> createChapter(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid CreateChapterRequest request
    ) {
        CreateChapterCommand command = new CreateChapterCommand(
                userId,
                request.seriesId(),
                request.chapterTitle(),
                request.chapterSubtitle(),
                request.chapterBody(),
                request.imageUrls(),
                ChapterStatus.from(request.status())
        );
        CreateChapterResponse response = chapterService.createChapter(command);
        return ResponseEntity.ok(response);

    }
}
