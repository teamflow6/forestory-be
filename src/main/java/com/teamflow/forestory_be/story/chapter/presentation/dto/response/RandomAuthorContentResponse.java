package com.teamflow.forestory_be.story.chapter.presentation.dto.response;

import com.teamflow.forestory_be.article.domain.entity.Article;
import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.chapter.domain.vo.AuthorContentType;

public record RandomAuthorContentResponse(
        AuthorContentType type,
        String id,
        String title,
        String seriesId,        // ARTICLE이면 null
        Integer chapterNumber // ARTICLE이면 null
) {
    public static RandomAuthorContentResponse ofChapter(Chapter c) {
        return new RandomAuthorContentResponse(
                AuthorContentType.CHAPTER,
                String.valueOf(c.getId()),
                c.getTitle().value(),
                String.valueOf(c.getSeriesId()),
                c.getChapterNumber()
        );
    }

    public static RandomAuthorContentResponse ofArticle(Article a) {
        return new RandomAuthorContentResponse(
                AuthorContentType.ARTICLE,
                String.valueOf(a.getId()),
                a.getTitle().value(),
                null,
                null
        );
    }
}
