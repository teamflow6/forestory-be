package com.teamflow.forestory_be.story.series.application.service;

import static com.teamflow.forestory_be.story.series.domain.vo.SeriesStatus.DRAFT_OVERVIEW;

import com.teamflow.forestory_be.story.series.application.dto.command.CreateSeriesCommand;
import com.teamflow.forestory_be.story.series.domain.entity.Series;
import com.teamflow.forestory_be.story.series.domain.repository.SeriesRepositoryPort;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesIntroduction;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesTitle;
import com.teamflow.forestory_be.story.series.domain.vo.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepositoryPort seriesRepositoryPort;

    @Transactional
    public Long createSeries(CreateSeriesCommand command) {
        SeriesTitle title = new SeriesTitle(command.seriesTitle());
        SeriesIntroduction introduction = new SeriesIntroduction(command.introduction());
        Type type = Type.from(command.type());
        if (command.status() == DRAFT_OVERVIEW) {
            Series series = Series.draft(command.authorId(), title, introduction, command.thumbnailUrl(), type);
            seriesRepositoryPort.save(series);
            return series.getId();
        } else {

            Series series = Series.create(command.authorId(), title, introduction, command.thumbnailUrl(), type);

            seriesRepositoryPort.save(series);
            return series.getId();
        }
    }
}
