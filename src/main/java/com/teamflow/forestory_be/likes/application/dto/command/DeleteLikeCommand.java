package com.teamflow.forestory_be.likes.application.dto.command;

import com.teamflow.forestory_be.likes.domain.vo.TargetType;

public record DeleteLikeCommand(
        Long userId,
        TargetType targetType,
        Long targetId
) { }