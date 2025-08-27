package com.teamflow.forestory_be.likes.infrastructure.persistence;

import com.teamflow.forestory_be.likes.domain.entity.Likes;
import com.teamflow.forestory_be.likes.domain.repository.LikeRepositoryPort;
import com.teamflow.forestory_be.likes.domain.vo.LikeStatus;
import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import com.teamflow.forestory_be.likes.infrastructure.persistence.entity.LikeJpaEntity;
import com.teamflow.forestory_be.likes.infrastructure.persistence.repository.LikeJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikePersistenceAdaptor implements LikeRepositoryPort {

    private final LikeJpaRepository likeJpaRepository;

    @Override
    public Likes save(Likes like) {
        LikeJpaEntity entity = LikePersistenceMapper.toJpaEntity(like);
        LikeJpaEntity saved = likeJpaRepository.save(entity);
        return LikePersistenceMapper.toDomainEntity(saved);
    }

    @Override
    public Optional<Likes> findByUserAndTarget(Long userId, TargetType targetType, Long targetId) {
        return likeJpaRepository
                .findByUserIdAndTargetTypeAndTargetId(userId, targetType, targetId)
                .map(LikePersistenceMapper::toDomainEntity);
    }

    @Override
    public List<Likes> sliceByUserUpdatedDesc(
            Long userId,
            LocalDateTime cursorUpdatedAt,
            Long cursorLikeId,
            int limitPlusOne
    ) {
        PageRequest page = PageRequest.of(0, limitPlusOne);

        List<LikeJpaEntity> rows = (cursorUpdatedAt == null || cursorLikeId == null)
                ? likeJpaRepository.firstPage(userId, page)
                : likeJpaRepository.nextPage(userId, cursorUpdatedAt, cursorLikeId, page);

        return rows.stream()
                .map(LikePersistenceMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByUserIdAndTargetIdsAndTargetType(Long userId, List<Long> targetIds, TargetType targetType) {
        likeJpaRepository.deleteAllByUserIdAndTargetIdsAndTargetType(userId, targetIds, targetType);
    }
}
