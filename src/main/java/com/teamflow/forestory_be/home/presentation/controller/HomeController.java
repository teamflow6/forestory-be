package com.teamflow.forestory_be.home.presentation.controller;

import com.teamflow.forestory_be.draft.domain.vo.DraftScope;
import com.teamflow.forestory_be.home.application.dto.query.GetTabCursorQuery;
import com.teamflow.forestory_be.home.application.dto.query.GetWeeklyTopByTypeQuery;
import com.teamflow.forestory_be.home.application.service.HomeQueryService;
import com.teamflow.forestory_be.home.presentation.dto.PopularCursorResponse;
import com.teamflow.forestory_be.home.presentation.dto.PopularListItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/home")
public class HomeController {

    private final HomeQueryService homeQueryService;

    // 홈 섹션: 타입별 N개 (주간 인기, 화 05:00 컷오프 기준)
    @GetMapping("/sections")
    @Operation(
            summary = "홈 섹션(주간 인기 TopN)",
            description = "NOVEL/ESSAY/ARTICLE 각각 주간 좋아요 순으로 N개 반환",
            security = { @SecurityRequirement(name = "AccessToken") }
    )
    public Map<String, List<PopularListItem>> sections(
            @RequestParam(defaultValue = "3") @Min(1) Integer limit
    ) {
        return homeQueryService.getWeeklyTopByType(GetWeeklyTopByTypeQuery.of(limit));
    }

    // 탭 목록: 커서 페이징 + 정렬(인기/최신)
    @GetMapping("/list-cursor")
    @Operation(
            summary = "탭 목록(커서 페이징): 인기/최신",
            description = "scope=ARTICLE|NOVEL|ESSAY, sort=POPULAR|LATEST, cursorId는 이전 페이지 마지막 id",
            security = { @SecurityRequirement(name = "AccessToken") }
    )
    public PopularCursorResponse listCursor(
            @RequestParam DraftScope scope,
            @RequestParam(defaultValue = "POPULAR") String sort,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "20") @Min(1) Integer size
    ) {
        return homeQueryService.getTabListByCursor(
                GetTabCursorQuery.of(scope, sort, cursorId, size)
        );
    }
}
