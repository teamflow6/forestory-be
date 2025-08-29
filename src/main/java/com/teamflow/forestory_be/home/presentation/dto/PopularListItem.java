package com.teamflow.forestory_be.home.presentation.dto;

import com.teamflow.forestory_be.draft.domain.vo.DraftScope;

public record PopularListItem(
        DraftScope type,
        String id,
        String title,
        String thumbnailUrl,
        long likeCount,
        long score
) {}
