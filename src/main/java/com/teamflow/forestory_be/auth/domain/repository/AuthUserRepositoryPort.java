package com.teamflow.forestory_be.auth.domain.repository;

import com.teamflow.forestory_be.auth.domain.entity.AuthUser;
import com.teamflow.forestory_be.auth.domain.vo.SocialType;
import java.util.Optional;

public interface AuthUserRepositoryPort {

    void save(AuthUser authUser);

    AuthUser getByEmail(String email);

    Optional<AuthUser> getByFirebaseUid(String uid);

    Optional<AuthUser> getBySocialIdAndType(String socialId, SocialType socialType);
}
