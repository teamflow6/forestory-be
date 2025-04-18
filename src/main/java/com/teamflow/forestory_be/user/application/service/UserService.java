package com.teamflow.forestory_be.user.application.service;

import com.teamflow.forestory_be.user.application.dto.CreateUserCommand;
import com.teamflow.forestory_be.user.application.dto.ReadUserQuery;
import com.teamflow.forestory_be.user.application.dto.UpdateUserCommand;
import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.domain.repository.UserRepositoryPort;
import com.teamflow.forestory_be.user.domain.vo.Name;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryPort userRepositoryPort;

    @Transactional
    public void create(CreateUserCommand command) {
        Name name = new Name(command.name());
        User user = User.create(
            name,
            command.profileImageUrl()
        );
        userRepositoryPort.save(user);
    }

    @Transactional
    public void update(UpdateUserCommand command) {
        User user = userRepositoryPort.getById(command.userId());
        Name name = new Name(command.name());
        User updatedUser = user.update(
            name,
            command.profileImageUrl()
        );
        userRepositoryPort.save(updatedUser);
    }

    public User getUserById(ReadUserQuery query) {
        return userRepositoryPort.getById(query.userId());
    }
}
