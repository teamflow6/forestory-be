package com.teamflow.forestory_be.article.infrastructure.persistence;

import com.teamflow.forestory_be.article.domain.entity.Article;
import com.teamflow.forestory_be.article.domain.exception.ArticleNotFoundException;
import com.teamflow.forestory_be.article.domain.repository.ArticleRepositoryPort;
import com.teamflow.forestory_be.article.infrastructure.persistence.entity.ArticleJpaEntity;
import com.teamflow.forestory_be.article.infrastructure.persistence.repository.ArticleJpaRepository;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ArticlePersistenceAdaptor implements ArticleRepositoryPort {

    private final ArticleJpaRepository articleJpaRepository;

    @Override
    public void save(Article article) {
        ArticleJpaEntity articleJpaEntity = ArticlePersistenceMapper.toJpaEntity(article);
        articleJpaRepository.save(articleJpaEntity);
    }

    @Override
    public Article getById(Long id) {
        ArticleJpaEntity articleJpaEntity = articleJpaRepository.findById(id)
                .orElseThrow(ArticleNotFoundException::new);
        return ArticlePersistenceMapper.toDomainEntity(articleJpaEntity);
    }

    @Override
    public void deleteById(Long id) {
        articleJpaRepository.deleteById(id);
    }

    @Override
    public List<Article> findRandomPublishedByAuthor(Long authorId, Long excludeArticleId, int limit) {
        return articleJpaRepository.pickRandomByAuthor(authorId, excludeArticleId, limit)
                .stream()
                .map(ArticlePersistenceMapper::toDomainEntity)
                .toList();
    }

    @Transactional
    @Override
    public void increaseLikeCount(Long articleId) {
        int updated = articleJpaRepository.increaseLikeCount(articleId);
        if (updated == 0) {
            throw new ArticleNotFoundException();
        }
    }

    @Transactional
    @Override
    public void decreaseLikeCount(Long articleId) {
        int updated = articleJpaRepository.decreaseLikeCount(articleId);
        if (updated == 0) {
            throw new ArticleNotFoundException();
        }
    }

    @Override
    @Transactional
    public int deleteArticles(Long userId, Collection<Long> targetIds) {
        if (targetIds == null || targetIds.isEmpty()) return 0;
        return articleJpaRepository.deleteArticles(userId, targetIds);
    }
}
