package com.teamflow.forestory_be.draft.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record GetDraftResponse(
        List<DraftListItem> items,
        LocalDateTime nextCursor,   // 다음 요청에 사용할 created_at
        boolean hasNext
) {
}
