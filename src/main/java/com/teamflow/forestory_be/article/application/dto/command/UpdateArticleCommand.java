package com.teamflow.forestory_be.article.application.dto.command;

import com.teamflow.forestory_be.article.domain.vo.ArticleStatus;

public record UpdateArticleCommand(
        Long articleId,
        Long authorId,
        String title,
        String subtitle,
        String content,
        String thumbnailUrl,
        ArticleStatus status
) {
}
