package com.teamflow.forestory_be.article.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.f4b6a3.tsid.TsidFactory;
import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import com.teamflow.forestory_be.article.domain.vo.Content;
import com.teamflow.forestory_be.article.domain.vo.Subtitle;
import com.teamflow.forestory_be.article.domain.vo.Title;
import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.domain.vo.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleTest {

    @BeforeEach
    void setUp() {
        TsidGenerator.initialize(TsidFactory.newInstance256(0));
    }

    @Test
    @DisplayName("아티클을 정상적으로 생성한다.")
    void should_create_article() {
        // given
        User author = User.create(new Name("이름"), "https://image.com/author.png");
        Title title = new Title("Article Title");
        Subtitle subtitle = new Subtitle("Article Subtitle");
        Content content = new Content("Article Content");
        String thumbnailUrl = "https://image.com/thumbnail.png";

        // when
        Article article = Article.create(author, title, subtitle, content, thumbnailUrl);

        // then
        assertThat(article.getId()).isNotNull();
        assertThat(article.getAuthor().getName()).isEqualTo(new Name("이름"));
        assertThat(article.getTitle()).isEqualTo(new Title("Article Title"));
        assertThat(article.getSubtitle()).isEqualTo(new Subtitle("Article Subtitle"));
        assertThat(article.getContent()).isEqualTo(new Content("Article Content"));
        assertThat(article.getThumbnailUrl()).isEqualTo(thumbnailUrl);
    }

    @Test
    @DisplayName("아티클을 임시 저장 상태로 생성한다.")
    void should_create_article_as_draft() {
        // given
        User author = User.create(new Name("이름"), "https://image.com/author.png");
        Title title = new Title("Draft Title");
        Subtitle subtitle = new Subtitle("Draft Subtitle");
        Content content = new Content("Draft Content");
        String thumbnailUrl = "https://image.com/thumbnail.png";

        // when
        Article draftArticle = Article.draft(author, title, subtitle, content, thumbnailUrl);

        // then
        assertThat(draftArticle.getId()).isNotNull();
        assertThat(draftArticle.getAuthor().getName()).isEqualTo(new Name("이름"));
        assertThat(draftArticle.getTitle()).isEqualTo(new Title("Draft Title"));
        assertThat(draftArticle.getSubtitle()).isEqualTo(new Subtitle("Draft Subtitle"));
        assertThat(draftArticle.getContent()).isEqualTo(new Content("Draft Content"));
        assertThat(draftArticle.getThumbnailUrl()).isEqualTo(thumbnailUrl);
    }
}
