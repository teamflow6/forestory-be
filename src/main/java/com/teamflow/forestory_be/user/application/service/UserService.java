package com.teamflow.forestory_be.user.application.service;

import com.teamflow.forestory_be.user.application.dto.CheckNameExistsQuery;
import com.teamflow.forestory_be.user.application.dto.CreateUserCommand;
import com.teamflow.forestory_be.user.application.dto.OnboardingUserCommand;
import com.teamflow.forestory_be.user.application.dto.ReadUserQuery;
import com.teamflow.forestory_be.user.application.dto.UpdateUserCommand;
import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.domain.exception.UserNameDuplicatedException;
import com.teamflow.forestory_be.user.domain.repository.UserRepositoryPort;
import com.teamflow.forestory_be.user.domain.vo.ContactUrl;
import com.teamflow.forestory_be.user.domain.vo.Introduction;
import com.teamflow.forestory_be.user.domain.vo.Name;
import com.teamflow.forestory_be.user.domain.vo.ProfileImageUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryPort userRepositoryPort;

    @Transactional
    public Long create(CreateUserCommand command) {
        Name name = new Name(command.name());
        ProfileImageUrl profileImageUrl = new ProfileImageUrl(command.profileImageUrl());
        User user = User.create(
            name,
            profileImageUrl
        );
        return userRepositoryPort.save(user);
    }

    @Transactional
    public User completeOnboarding(OnboardingUserCommand command) {
        User user = userRepositoryPort.getById(command.userId());
        Name name = new Name(command.name());
        ProfileImageUrl profileImageUrl = new ProfileImageUrl(command.profileImageUrl());

        validateNameDuplicated(name);

        User updatedUser = user.completeOnboarding(command.userId(), name, profileImageUrl);
        userRepositoryPort.save(updatedUser);
        return updatedUser;
    }

    @Transactional
    public User update(UpdateUserCommand command) {
        User user = userRepositoryPort.getById(command.userId());
        Name name = new Name(command.name());
        Introduction introduction = Introduction.fromNullable(command.introduction(), user.getIntroduction());
        ProfileImageUrl profileImageUrl =
            ProfileImageUrl.fromNullable(command.profileImageUrl(), user.getProfileImageUrl());
        ContactUrl contactUrl = ContactUrl.fromNullable(command.contactUrl(), user.getContactUrl());

        if (!user.isSameName(name)) {
            validateNameDuplicated(name);
        }

        User updatedUser = user.update(command.userId(), name, introduction, profileImageUrl, contactUrl);
        userRepositoryPort.save(updatedUser);
        return updatedUser;
    }

    public User getUserById(ReadUserQuery query) {
        return userRepositoryPort.getById(query.userId());
    }

    public boolean existsByName(CheckNameExistsQuery query) {
        Name name = new Name(query.name());
        return userRepositoryPort.existsByName(name);
    }

    private void validateNameDuplicated(Name name) {
        if (userRepositoryPort.existsByName(name)) {
            throw new UserNameDuplicatedException();
        }
    }
}
