package com.teamflow.forestory_be.article.application.dto.command;

public record DeleteArticleCommand(
        Long articleId,
        Long requesterId
) {
}
