package com.teamflow.forestory_be.story.series.domain.entity;

import com.teamflow.forestory_be.article.domain.exception.InvalidArticleOwnerException;
import com.teamflow.forestory_be.article.domain.vo.Title;
import com.teamflow.forestory_be.story.series.domain.exception.InvalidSeriesOwnerException;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesIntroduction;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesTitle;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesStatus;
import com.teamflow.forestory_be.story.series.domain.vo.Type;
import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Series {
    private final Long id;
    private final Long authorId;
    private final SeriesTitle title;
    private final SeriesIntroduction introduction;
    private final String thumbnailUrl;
    private final Type type;
    private final SeriesStatus seriesStatus;

    private Series(Long id, Long authorId, SeriesTitle title, SeriesIntroduction introduction,
                   String thumbnailUrl, Type type, SeriesStatus seriesStatus) {
        this.id = Objects.requireNonNull(id);
        this.authorId = Objects.requireNonNull(authorId);
        this.title = Objects.requireNonNull(title);
        this.introduction = Objects.requireNonNull(introduction);
        this.thumbnailUrl = thumbnailUrl;
        this.type = Objects.requireNonNull(type);
        this.seriesStatus = Objects.requireNonNull(seriesStatus);
    }

    public static Series reconstruct(Long id, Long authorId, SeriesTitle title,
                                     SeriesIntroduction introduction, String thumbnailUrl,
                                     Type type, SeriesStatus seriesStatus) {
        return new Series(id, authorId, title, introduction, thumbnailUrl, type, seriesStatus);
    }

    public static Series create(Long authorId, SeriesTitle title, SeriesIntroduction introduction,
                                String thumbnailUrl, Type type) {
        Long id = TsidGenerator.generate();
        return new Series(id, authorId, title, introduction, thumbnailUrl, type, SeriesStatus.PENDING_FIRST_CHAPTER);
    }

    public Series update(SeriesTitle title, SeriesIntroduction introduction, String thumbnailUrl, Type type, SeriesStatus seriesStatus) {
        return new Series(
                this.id,
                this.authorId,
                title,
                introduction,
                thumbnailUrl,
                type,
                seriesStatus
        );
    }

    public void validateOwnerOrThrow(Long userId) {
        if (authorId != userId) {
            throw new InvalidSeriesOwnerException();
        }
    }




}
