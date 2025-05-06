package com.teamflow.forestory_be.user.domain.repository;

import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.domain.vo.Name;

public interface UserRepositoryPort {
    Long save(User user);

    User getById(Long userId);

    Boolean existsByName(Name name);
}
