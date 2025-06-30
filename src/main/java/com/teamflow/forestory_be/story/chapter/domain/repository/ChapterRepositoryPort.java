package com.teamflow.forestory_be.story.chapter.domain.repository;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;

public interface ChapterRepositoryPort {
    void save(Chapter chapter);

    String findLastChapterNumber(Long seriesId);
}
