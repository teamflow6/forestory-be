package com.teamflow.forestory_be.likes.infrastructure.persistence;

import com.teamflow.forestory_be.likes.domain.entity.Likes;
import com.teamflow.forestory_be.likes.domain.repository.LikeRepositoryPort;
import com.teamflow.forestory_be.likes.domain.vo.LikeStatus;
import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import com.teamflow.forestory_be.likes.infrastructure.persistence.entity.LikeJpaEntity;
import com.teamflow.forestory_be.likes.infrastructure.persistence.repository.LikeJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
}
