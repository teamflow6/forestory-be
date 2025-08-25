package com.teamflow.forestory_be.article.domain.entity;

import com.teamflow.forestory_be.article.domain.exception.InvalidArticleOwnerException;
import com.teamflow.forestory_be.article.domain.exception.InvalidArticleStatusException;
import com.teamflow.forestory_be.article.domain.vo.ArticleStatus;
import com.teamflow.forestory_be.article.domain.vo.Content;
import com.teamflow.forestory_be.article.domain.vo.Subtitle;
import com.teamflow.forestory_be.article.domain.vo.Title;
import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Article {
    private final Long id;
    private final Long authorId;
    private final Title title;
    private final Subtitle subtitle;
    private final Content content;
    private final String thumbnailUrl;
    private final long likeCount;
    private final ArticleStatus status;

    private Article(Long id,
                    Long authorId,
                    Title title,
                    Subtitle subtitle,
                    Content content,
                    String thumbnailUrl,
                    long likeCount,
                    ArticleStatus status) {
        this.id = Objects.requireNonNull(id);
        this.authorId = Objects.requireNonNull(authorId);
        this.title = Objects.requireNonNull(title);
        this.subtitle = Objects.requireNonNull(subtitle);
        this.content = Objects.requireNonNull(content);
        this.thumbnailUrl = thumbnailUrl;
        this.likeCount = likeCount;
        this.status = Objects.requireNonNull(status);
    }

    public static Article create(Long authorId, Title title, Subtitle subtitle, Content content, String thumbnailUrl) {
        Long id = TsidGenerator.generate();
        return new Article(id, authorId, title, subtitle, content, thumbnailUrl, 0, ArticleStatus.PUBLISHED);
    }

    public static Article draft(Long authorId, Title title, Subtitle subtitle, Content content, String thumbnailUrl) {
        Long id = TsidGenerator.generate();
        return new Article(id, authorId, title, subtitle, content, thumbnailUrl, 0, ArticleStatus.DRAFT);
    }

    public static Article reconstruct(Long id, Long authorId, Title title, Subtitle subtitle, Content content,
                                      String thumbnailUrl, long likeCount, ArticleStatus status) {
        return new Article(id, authorId, title, subtitle, content, thumbnailUrl, likeCount, status);
    }

    public Article update(Title title, Subtitle subtitle, Content content, String thumbnailUrl, ArticleStatus status) {
        return new Article(
                this.id,
                this.authorId,
                title,
                subtitle,
                content,
                thumbnailUrl,
                this.likeCount,
                status
        );
    }

    public Article updateStatus(ArticleStatus newStatus) {
        if (this.status == newStatus) {
            throw new InvalidArticleStatusException("이미 동일한 상태입니다.");
        }
        return new Article(
                this.id,
                this.authorId,
                this.title,
                this.subtitle,
                this.content,
                this.thumbnailUrl,
                this.likeCount,
                newStatus
        );
    }

    public void validateOwnerOrThrow(Long userId) {
        if (!authorId.equals(userId)) {
            throw new InvalidArticleOwnerException();
        }
    }
}
