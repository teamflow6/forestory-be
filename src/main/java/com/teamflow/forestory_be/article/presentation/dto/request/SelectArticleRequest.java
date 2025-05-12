package com.teamflow.forestory_be.article.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record SelectArticleRequest(

        @NotNull(message = "아티클 ID는 필수입니다.")
        Long authorId
) {
}
