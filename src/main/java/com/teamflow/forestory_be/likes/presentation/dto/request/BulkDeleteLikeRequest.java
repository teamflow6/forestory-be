package com.teamflow.forestory_be.likes.presentation.dto.request;

import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public record BulkDeleteLikeRequest(
        @NotNull
        @NotEmpty(message = "삭제할 대상이 비어있을 수 없습니다.")
        Map<@NotNull TargetType, @NotEmpty(message = "각 타입의 ID 목록은 비어있을 수 없습니다.") List<@NotNull Long>> targets
) {}
