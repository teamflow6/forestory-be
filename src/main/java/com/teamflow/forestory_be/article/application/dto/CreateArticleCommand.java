package com.teamflow.forestory_be.article.application.dto;

public record CreateArticleCommand(
        Long authorId,
        String title,
        String subtitle,
        String content,
        String thumbnailUrl,
        boolean isDraft
) {

}
