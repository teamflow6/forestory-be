package com.teamflow.forestory_be.home.presentation.dto;

import java.util.List;

public record PopularCursorResponse(
        List<PopularListItem> items,
        Long nextCursorId,   // 다음 요청 시 ?cursorId=<이값>, 더 없으면 null
        boolean hasNext
) {
    public static PopularCursorResponse of(List<PopularListItem> items, int pageSize) {
        boolean hasNext = items.size() == pageSize;
        Long next = null;
        if (hasNext) {
            String lastId = items.get(items.size() - 1).id();
            try {
                next = Long.parseLong(lastId);
            } catch (NumberFormatException ignore) {
                // 숫자 변환 불가면 nextCursorId는 null 처리
            }
        }
        return new PopularCursorResponse(items, next, hasNext);
    }
}
