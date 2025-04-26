package com.teamflow.forestory_be.auth.infrastructure.persistence;

import com.teamflow.forestory_be.auth.domain.entity.AuthUser;
import com.teamflow.forestory_be.auth.domain.repository.AuthUserRepositoryPort;
import com.teamflow.forestory_be.auth.domain.vo.SocialType;
import com.teamflow.forestory_be.auth.infrastructure.persistence.entity.AuthUserJpaEntity;
import com.teamflow.forestory_be.auth.infrastructure.persistence.repository.AuthUserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUserPersistenceAdapter implements AuthUserRepositoryPort {

    private final AuthUserJpaRepository authUserJpaRepository;

    @Override
    public void save(AuthUser authUser) {
        AuthUserJpaEntity authUserJpaEntity = AuthUserPersistenceMapper.toJpaEntity(authUser);
        authUserJpaRepository.save(authUserJpaEntity);
    }

    @Override
    public AuthUser getByEmail(String email) {
        return authUserJpaRepository.findByEmail(email)
            .map(AuthUserPersistenceMapper::toDomainEntity)
            .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public AuthUser getByFirebaseUid(String token) {
        return authUserJpaRepository.findByFirebaseUid(token)
            .map(AuthUserPersistenceMapper::toDomainEntity)
            .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Optional<AuthUser> getBySocialIdAndType(String socialId, SocialType socialType) {
        return authUserJpaRepository.findBySocialIdAndSocialType(socialId, socialType)
            .map(AuthUserPersistenceMapper::toDomainEntity);
    }
}
