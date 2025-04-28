package com.teamflow.forestory_be.article.domain.entity;

import com.teamflow.forestory_be.article.domain.vo.ArticleStatus;
import com.teamflow.forestory_be.article.domain.vo.Content;
import com.teamflow.forestory_be.article.domain.vo.Subtitle;
import com.teamflow.forestory_be.article.domain.vo.Title;
import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import com.teamflow.forestory_be.user.domain.entity.User;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Article {
    private final Long id;
    private final User author;
    private final Title title;
    private final Subtitle subtitle;
    private final Content content;
    private final String thumbnailUrl;
    private final ArticleStatus status;

    private Article(Long id, User author, Title title, Subtitle subtitle,
                   Content content, String thumbnailUrl, ArticleStatus status) {
        this.id = Objects.requireNonNull(id);
        this.author = Objects.requireNonNull(author);
        this.title = Objects.requireNonNull(title);
        this.subtitle = Objects.requireNonNull(subtitle);
        this.content = Objects.requireNonNull(content);
        this.thumbnailUrl = thumbnailUrl;
        this.status = Objects.requireNonNull(status);
    }

    public static Article create(User author, Title title, Subtitle subtitle, Content content, String thumbnailUrl) {
        Long id = TsidGenerator.generate();
        return new Article(id, author, title, subtitle, content, thumbnailUrl, ArticleStatus.PUBLISHED);
    }

    public static Article draft(User author, Title title, Subtitle subtitle, Content content, String thumbnailUrl) {
        Long id = TsidGenerator.generate();
        return new Article(id, author, title, subtitle, content, thumbnailUrl, ArticleStatus.DRAFT);
    }

    public static Article reconstruct(Long id, User author, Title title, Subtitle subtitle, Content content, String thumbnailUrl, ArticleStatus status) {
        return new Article(id,author, title, subtitle, content, thumbnailUrl, status);
    }



}
