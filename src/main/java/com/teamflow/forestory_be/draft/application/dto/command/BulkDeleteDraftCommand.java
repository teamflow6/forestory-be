// com.teamflow.forestory_be.draft.application.dto.command.BulkDeleteDraftCommand
package com.teamflow.forestory_be.draft.application.dto.command;

import com.teamflow.forestory_be.draft.domain.vo.DraftScope;
import java.util.List;

public record BulkDeleteDraftCommand(
        Long userId,
        List<Item> items
) {
    public record Item(DraftScope scope, Long targetId) { }
}
