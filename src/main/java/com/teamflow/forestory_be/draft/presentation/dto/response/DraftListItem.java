package com.teamflow.forestory_be.draft.presentation.dto.response;

import com.teamflow.forestory_be.draft.domain.vo.DraftScope;
import java.time.LocalDateTime;

public record DraftListItem(
        String id,
        String title,
        String subtitle,
        Integer chapterNumber,
        DraftScope scope,
        LocalDateTime createdAt
) {
}
