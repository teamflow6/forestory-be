package com.teamflow.forestory_be.article.application.dto.command;

import com.teamflow.forestory_be.article.domain.vo.ArticleStatus;

public record UpdateArticleStatusCommand(
        Long articleId,
        Long authorId,
        ArticleStatus status
) {
}
