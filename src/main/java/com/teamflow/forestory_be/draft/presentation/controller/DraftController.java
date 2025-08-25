package com.teamflow.forestory_be.draft.presentation.controller;

import com.teamflow.forestory_be.draft.application.dto.command.BulkDeleteDraftCommand;
import com.teamflow.forestory_be.draft.application.dto.command.BulkDeleteDraftCommand.Item;
import com.teamflow.forestory_be.draft.application.dto.query.GetDraftsQuery;
import com.teamflow.forestory_be.draft.application.service.DraftService;
import com.teamflow.forestory_be.draft.domain.vo.DraftScope;
import com.teamflow.forestory_be.draft.presentation.dto.request.BulkDeleteDraftRequest;
import com.teamflow.forestory_be.draft.presentation.dto.response.BulkDeleteDraftResponse;
import com.teamflow.forestory_be.draft.presentation.dto.response.GetDraftResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/drafts")
@RequiredArgsConstructor
@Validated
public class DraftController {

    private final DraftService draftService;

    @GetMapping
    @Operation(
            summary = "임시저장글 무한 스크롤 조회",
            description = "아티클/챕터(소설, 에세이)/전체 임시저장글 목록을 커서 기반으로 최신순 조회합니다.",
            security = @SecurityRequirement(name = "AccessToken")
    )
    public ResponseEntity<GetDraftResponse> getDrafts(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long userId,

            @Parameter(description = "조회 범위 (ALL, ARTICLE, NOVEL, ESSAY)", required = true)
            @RequestParam("scope") DraftScope scope,

            @Parameter(description = "커서 createdAt (첫 요청 시 null)")
            @RequestParam(value = "cursor", required = false) LocalDateTime cursorCreatedAt,

            @Parameter(description = "가져올 데이터 개수 (기본값 20)")
            @RequestParam(value = "size", defaultValue = "20") @Min(1) int size
    ) {
        GetDraftsQuery query = new GetDraftsQuery(
                userId,
                scope,
                cursorCreatedAt,
                size
        );
        GetDraftResponse response = draftService.getDrafts(query);
        return ResponseEntity.ok(response);
    }

    // DraftController.java
    @DeleteMapping("/bulk-delete")
    @Operation(summary = "임시저장 글 일괄 삭제", description = "체크한 임시저장 글(ARTICLE/NOVEL/ESSAY)을 한 번에 삭제합니다.",
            security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<BulkDeleteDraftResponse> bulkDelete(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @RequestBody @Valid BulkDeleteDraftRequest request
    ) {
        // Request.Item -> Command.Item 매핑
        List<Item> items = request.items().stream()
                .map(i -> new BulkDeleteDraftCommand.Item(i.scope(), Long.parseLong(i.targetId())))
                .collect(Collectors.toList());

        BulkDeleteDraftCommand command = new BulkDeleteDraftCommand(userId, items); // ← 세미콜론 추가
        return ResponseEntity.ok(draftService.bulkDelete(command));
    }


}
