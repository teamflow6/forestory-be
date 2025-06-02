package com.teamflow.forestory_be.article.application.dto.command;

public record UpdateArticleCommand(
        Long articleId,
        Long authorId,
        String title,
        String subtitle,
        String content,
        String thumbnailUrl,
        String status
) {
}
