package com.teamflow.forestory_be.story.chapter.presentation.controller;

import com.google.api.gax.rpc.UnauthenticatedException;
import com.teamflow.forestory_be.auth.domain.entity.AuthUser;
import com.teamflow.forestory_be.auth.infrastructure.security.oauth.CustomOAuth2User;
import com.teamflow.forestory_be.story.chapter.application.dto.command.CreateChapterCommand;
import com.teamflow.forestory_be.story.chapter.application.dto.command.UpdateChapterCommand;
import com.teamflow.forestory_be.story.chapter.application.dto.query.GetChapterQuery;
import com.teamflow.forestory_be.story.chapter.application.service.ChapterService;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterStatus;
import com.teamflow.forestory_be.story.chapter.presentation.dto.request.CreateChapterRequest;
import com.teamflow.forestory_be.story.chapter.presentation.dto.request.UpdateChapterRequest;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.CreateChapterResponse;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.GetChapterResponse;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.UpdateChapterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping("/{chapterId}")
    public ResponseEntity<GetChapterResponse> getChapter(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long chapterId
    ) {

        GetChapterQuery query = new GetChapterQuery(chapterId, userId);
        GetChapterResponse response = chapterService.getChapter(query);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{chapterId}")
    public ResponseEntity<UpdateChapterResponse> updateChapter(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long chapterId,
            @RequestBody @Valid UpdateChapterRequest request
    ) {
        UpdateChapterCommand command = new UpdateChapterCommand(
                chapterId,
                request.seriesId(),
                userId,
                request.title(),
                request.subtitle(),
                request.body(),
                ChapterStatus.from(request.status()),
                request.imageUrls(),
                request.chapterNumber()
        );

        UpdateChapterResponse response = chapterService.updateChapter(command);
        return ResponseEntity.ok(response);
    }




}
