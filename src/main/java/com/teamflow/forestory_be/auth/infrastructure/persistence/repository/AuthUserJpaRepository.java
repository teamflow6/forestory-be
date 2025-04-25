package com.teamflow.forestory_be.auth.infrastructure.persistence.repository;

import com.teamflow.forestory_be.auth.domain.vo.SocialType;
import com.teamflow.forestory_be.auth.infrastructure.persistence.entity.AuthUserJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserJpaRepository extends JpaRepository<AuthUserJpaEntity, Long> {

    Optional<AuthUserJpaEntity> findByEmail(String email);

    Optional<AuthUserJpaEntity> findByFirebaseUid(String uid);

    Optional<AuthUserJpaEntity> findBySocialIdAndSocialType(String socialId, SocialType socialType);
}
