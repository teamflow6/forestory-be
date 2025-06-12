package com.teamflow.forestory_be.article.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateArticleStatusRequest(
        @NotNull
        String status
) {
}
