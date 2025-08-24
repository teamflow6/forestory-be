package com.teamflow.forestory_be.likes.infrastructure.persistence;

import com.teamflow.forestory_be.article.domain.repository.ArticleRepositoryPort;
import com.teamflow.forestory_be.likes.domain.repository.LikeCounterPort;
import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import com.teamflow.forestory_be.story.chapter.domain.repository.ChapterRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LikeCounterAdaptor implements LikeCounterPort {

    private final ArticleRepositoryPort articleRepositoryPort;
    private final ChapterRepositoryPort chapterRepositoryPort;

    @Transactional
    @Override
    public void increase(TargetType type, Long targetId) {
        switch (type) {
            case ARTICLE -> articleRepositoryPort.increaseLikeCount(targetId);
            case ESSAY -> chapterRepositoryPort.increaseLikeCount(targetId);
            case NOVEL -> chapterRepositoryPort.increaseLikeCount(targetId);

        }
    }

    @Transactional
    @Override
    public void decrease(TargetType type, Long targetId) {
        switch (type) {
            case ARTICLE -> articleRepositoryPort.decreaseLikeCount(targetId);
            case ESSAY -> chapterRepositoryPort.decreaseLikeCount(targetId);
            case NOVEL -> chapterRepositoryPort.decreaseLikeCount(targetId);


        }
    }
}
