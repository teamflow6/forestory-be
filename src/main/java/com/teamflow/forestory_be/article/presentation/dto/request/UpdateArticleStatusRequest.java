package com.teamflow.forestory_be.article.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UpdateArticleStatusRequest(

        @NotNull
        @Schema(description = "게시글 상태 (PUBLISHED 또는 DRAFT)", example = "DRAFT")
        String status

) {
}
