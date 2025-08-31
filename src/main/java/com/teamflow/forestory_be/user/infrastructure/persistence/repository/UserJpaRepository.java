package com.teamflow.forestory_be.user.infrastructure.persistence.repository;

import com.teamflow.forestory_be.user.infrastructure.persistence.entity.UserJpaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    Boolean existsByName(String name);

    List<UserJpaEntity> findAllByIdIn(List<Long> ids);
}
