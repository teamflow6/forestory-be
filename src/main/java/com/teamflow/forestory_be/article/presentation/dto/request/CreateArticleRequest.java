package com.teamflow.forestory_be.article.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateArticleRequest(

        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @NotBlank(message = "부제목은 필수입니다.")
        String subtitle,

        @NotBlank(message = "본문은 필수입니다.")
        String content,

        String thumbnailUrl,

        @NotNull
        String status
) {

}
