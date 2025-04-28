package com.teamflow.forestory_be.article.presentation.request;

public record CreateArticleRequest(
    Long authorId,
    String title,
    String subtitle,
    String content,
    String thumbnailUrl) {

}
