package com.teamflow.forestory_be.likes.presentation.dto.response;

import com.teamflow.forestory_be.draft.domain.vo.DraftScope;
import java.time.LocalDateTime;

public record LikeListItem(
        String targetId,
        DraftScope targetType,   // ARTICLE / NOVEL / ESSAY
        String title,
        String subtitle,
        String thumbnailUrl,
        Integer chapterNumber,   // ARTICLE이면 null
        LocalDateTime updatedAt  // likes.updated_at
) { }