package com.teamflow.forestory_be.article.application.dto.command;

public record CreateArticleCommand(
        Long authorId,
        String title,
        String subtitle,
        String content,
        String thumbnailUrl,
#35        String status
) {

}
