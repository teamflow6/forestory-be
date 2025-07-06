package com.teamflow.forestory_be.story.series.application.service;

import com.teamflow.forestory_be.story.chapter.domain.repository.ChapterRepositoryPort;
import com.teamflow.forestory_be.story.series.application.dto.command.CreateSeriesCommand;
import com.teamflow.forestory_be.story.series.application.dto.query.GetSeriesQuery;
import com.teamflow.forestory_be.story.series.domain.entity.Series;
import com.teamflow.forestory_be.story.series.domain.repository.SeriesRepositoryPort;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesIntroduction;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesStatus;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesTitle;
import com.teamflow.forestory_be.story.series.domain.vo.Type;
import com.teamflow.forestory_be.story.series.presentation.dto.response.ChapterResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.ChapterWithCreatedAt;
import com.teamflow.forestory_be.story.series.presentation.dto.response.GetSeriesResponse;
import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.domain.repository.UserRepositoryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepositoryPort seriesRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final ChapterRepositoryPort chapterRepositoryPort;

    @Transactional
    public Long createSeries(CreateSeriesCommand command) {
        SeriesTitle title = new SeriesTitle(command.seriesTitle());
        SeriesIntroduction introduction = new SeriesIntroduction(command.introduction());
        Type type = Type.from(command.type());
        if (command.status() == SeriesStatus.DRAFT_OVERVIEW) {
            Series series = Series.draft(command.authorId(), title, introduction, command.thumbnailUrl(), type);
            seriesRepositoryPort.save(series);
            return series.getId();
        } else {

            Series series = Series.create(command.authorId(), title, introduction, command.thumbnailUrl(), type);

            seriesRepositoryPort.save(series);
            return series.getId();
        }
    }

    @Transactional(readOnly = true)
    public GetSeriesResponse getSeries(GetSeriesQuery query) {
        Series series = seriesRepositoryPort.getById(query.seriesId());
        User author = userRepositoryPort.getById(series.getAuthorId());

        List<ChapterWithCreatedAt> publishedChapters = chapterRepositoryPort
                .findPublishedChaptersWithScroll(
                        query.seriesId(),
                        query.sort(),
                        String.valueOf(query.lastChapterNumber()),
                        query.size()
                );

        List<ChapterResponse> chapterResponses = publishedChapters.stream()
                .map(ChapterResponse::from)  // ChapterResponse.from(ChapterWithCreatedAt) 필요
                .toList();

        return GetSeriesResponse.of(series, chapterResponses, author);
    }

}
