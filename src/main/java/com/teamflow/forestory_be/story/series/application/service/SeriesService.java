package com.teamflow.forestory_be.story.series.application.service;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.chapter.domain.repository.ChapterRepositoryPort;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterStatus;
import com.teamflow.forestory_be.story.series.application.dto.command.CompleteSeriesCommand;
import com.teamflow.forestory_be.story.series.application.dto.command.CreateSeriesCommand;
import com.teamflow.forestory_be.story.series.application.dto.command.DeleteLatestChapterCommand;
import com.teamflow.forestory_be.story.series.application.dto.command.DeleteSeriesCommand;
import com.teamflow.forestory_be.story.series.application.dto.command.UpdateSeriesCommand;
import com.teamflow.forestory_be.story.series.application.dto.query.GetNextChapterInfoQuery;
import com.teamflow.forestory_be.story.series.application.dto.query.GetSeriesListQuery;
import com.teamflow.forestory_be.story.series.application.dto.query.GetSeriesQuery;
import com.teamflow.forestory_be.story.series.domain.entity.Series;
import com.teamflow.forestory_be.story.series.domain.repository.SeriesRepositoryPort;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesIntroduction;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesStatus;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesTitle;
import com.teamflow.forestory_be.story.series.domain.vo.Type;
import com.teamflow.forestory_be.story.series.presentation.dto.response.ChapterResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.ChapterWithCreatedAt;
import com.teamflow.forestory_be.story.series.presentation.dto.response.CompleteSeriesResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.DeleteLatestChapterResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.DeleteSeriesResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.GetNextChapterInfoResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.GetSeriesListResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.GetSeriesResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.UpdateSeriesResponse;
import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.domain.repository.UserRepositoryPort;
import java.time.LocalDateTime;
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

        Series series = Series.create(command.authorId(), title, introduction, command.thumbnailUrl(), type);
        seriesRepositoryPort.save(series);

        return series.getId();
    }

    @Transactional(readOnly = true)
    public GetSeriesResponse getSeries(GetSeriesQuery query) {
        Series series = seriesRepositoryPort.getById(query.seriesId());
        User author = userRepositoryPort.getById(series.getAuthorId());

        List<ChapterWithCreatedAt> publishedChapters = chapterRepositoryPort
                .findPublishedChaptersWithScroll(
                        query.seriesId(),
                        query.sort(),
                        query.lastChapterNumber(),
                        query.size()
                );

        List<ChapterResponse> chapterResponses = publishedChapters.stream()
                .map(ChapterResponse::from)
                .toList();

        long seriesLikeCount = chapterRepositoryPort
                .sumLikeCountBySeriesAndStatus(query.seriesId(), ChapterStatus.PUBLISHED);

        return GetSeriesResponse.of(series, chapterResponses, author,seriesLikeCount);
    }

    @Transactional(readOnly = true)
    public GetNextChapterInfoResponse getNextChapterInfo(GetNextChapterInfoQuery query) {
        // 시리즈 존재 여부 검증
        Series series = seriesRepositoryPort.getById(query.seriesId());

        // 최신 회차 번호 조회 (없으면 0)
        Integer lastChapterNumber = chapterRepositoryPort.findLastChapterNumber(series.getId());

        return GetNextChapterInfoResponse.of(
                series.getId().toString(),
                series.getTitle().value(),
                lastChapterNumber + 1
        );
    }


    @Transactional(readOnly = true)
    public List<GetSeriesListResponse> getSeriesList(GetSeriesListQuery q) {
        LocalDateTime lastCreatedAt = q.lastCreatedAt();
        int size = q.size();

        if ("all".equalsIgnoreCase(q.type())) {
            // 전체 타입에서 PUBLISHED 상태만
            return seriesRepositoryPort.findByAuthorAndStatus(
                    q.userId(), SeriesStatus.PUBLISHED, lastCreatedAt, size);
        } else {
            // 특정 타입 + PUBLISHED 상태
            Type type = Type.from(q.type());
            return seriesRepositoryPort.findByAuthorAndTypeAndStatus(
                    q.userId(), type, SeriesStatus.PUBLISHED, lastCreatedAt, size);
        }
    }




    @Transactional
    public UpdateSeriesResponse update(UpdateSeriesCommand command) {
        Series existingSeries = seriesRepositoryPort.getById(command.seriesId());
        existingSeries.validateOwnerOrThrow(command.authorId());

        Series updatedSeries = existingSeries.update(
                new SeriesTitle(command.title()),
                new SeriesIntroduction(command.introduction()),
                command.thumbnailUrl(),
                command.type(),
                command.seriesStatus()
        );

        seriesRepositoryPort.save(updatedSeries);

        return UpdateSeriesResponse.from(command.seriesId());
    }

    @Transactional
    public CompleteSeriesResponse completeSeries(CompleteSeriesCommand command) {
        Series series = seriesRepositoryPort.getById(command.seriesId());

        series.validateOwnerOrThrow(command.authorId());

        // 새로운 Series 반환
        Series updated = series.changeStatus(SeriesStatus.COMPLETED);

        // DB에 반영
        seriesRepositoryPort.save(updated);

        return CompleteSeriesResponse.of(updated.getId().toString(), updated.getSeriesStatus().toString());
    }


    @Transactional
    public DeleteSeriesResponse delete(DeleteSeriesCommand command) {
        Series series = seriesRepositoryPort.getById(command.seriesId());

        series.validateOwnerOrThrow(command.authorId());

        chapterRepositoryPort.deleteAllBySeriesId(command.seriesId());
        seriesRepositoryPort.deleteById(command.seriesId());

        return DeleteSeriesResponse.from(command.seriesId());
    }

    @Transactional
    public DeleteLatestChapterResponse deleteLatestChapter(DeleteLatestChapterCommand command) {
        // 시리즈 가져오기
        Series series = seriesRepositoryPort.getById(command.seriesId());
        series.validateOwnerOrThrow(command.authorId());

        // 최신 챕터 찾기 (예: number 기준 desc 정렬 후 첫 번째)
        Chapter latestChapter = chapterRepositoryPort
                .findTopBySeriesIdOrderByChapterNumberDesc(command.seriesId());


        // 삭제
        chapterRepositoryPort.delete(latestChapter);

        return DeleteLatestChapterResponse.of(latestChapter.getId().toString(), command.seriesId().toString());
    }

}

