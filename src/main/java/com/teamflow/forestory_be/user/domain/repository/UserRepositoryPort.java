package com.teamflow.forestory_be.user.domain.repository;

import com.teamflow.forestory_be.user.domain.entity.User;

public interface UserRepositoryPort {
    void save(User user);

    User getById(Long userId);
}
