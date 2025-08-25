package com.teamflow.forestory_be.draft.presentation.dto.request;

import com.teamflow.forestory_be.draft.domain.vo.DraftScope;
import java.util.List;

public record BulkDeleteDraftRequest(
        List<Item> items
) {
    public record Item(
             DraftScope scope, // ARTICLE | NOVEL | ESSAY  (ALL 금지)
             String targetId   // Long 문자열
    ) { }
}
