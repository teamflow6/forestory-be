package com.teamflow.forestory_be.draft.application.dto.query;

import com.teamflow.forestory_be.draft.domain.vo.DraftScope;
import java.time.LocalDateTime;

public record GetDraftsQuery(
        Long userId,
        DraftScope draftScope,
        LocalDateTime cursorCreatedAt,
        int size
) {
}
