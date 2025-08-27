package com.teamflow.forestory_be.likes.application.dto.command;

import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import java.util.List;
import java.util.Map;

public record BulkDeleteLikeCommand(
        Long userId,
        Map<TargetType, List<Long>> targets
) {}
