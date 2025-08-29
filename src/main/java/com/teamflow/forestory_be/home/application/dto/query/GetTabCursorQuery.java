package com.teamflow.forestory_be.home.application.dto.query;

import com.teamflow.forestory_be.draft.domain.vo.DraftScope;
import com.teamflow.forestory_be.home.domain.vo.TabSort;

public record GetTabCursorQuery(
        DraftScope scope,    // ARTICLE | NOVEL | ESSAY
        TabSort sort,        // POPULAR | LATEST
        Long cursorId,       // null이면 첫 페이지
        int size             // page size
) {
    public static GetTabCursorQuery of(DraftScope scope, String sort, Long cursorId, Integer size) {
        return new GetTabCursorQuery(
                scope,
                TabSort.from(sort),
                cursorId,
                Math.max(1, size == null ? 20 : size)
        );
    }
}
