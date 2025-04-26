package com.teamflow.forestory_be.auth.application;

import com.teamflow.forestory_be.auth.application.dto.DeleteTokenCommand;
import com.teamflow.forestory_be.auth.application.dto.IssueTokenCommand;
import com.teamflow.forestory_be.auth.application.dto.ReissueTokenCommand;
import com.teamflow.forestory_be.auth.application.dto.SocialLoginCommand;
import com.teamflow.forestory_be.auth.domain.entity.AuthUser;
import com.teamflow.forestory_be.auth.domain.entity.Token;
import com.teamflow.forestory_be.auth.domain.repository.AuthUserRepositoryPort;
import com.teamflow.forestory_be.auth.domain.repository.TokenRepositoryPort;
import com.teamflow.forestory_be.user.application.dto.CreateUserCommand;
import com.teamflow.forestory_be.user.application.service.UserService;
import com.teamflow.forestory_be.user.domain.policy.RandomNameGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenRepositoryPort tokenRepositoryPort;
    private final AuthUserRepositoryPort authUserRepositoryPort;

    @Transactional
    public Long socialLogin(SocialLoginCommand command) {
        return authUserRepositoryPort.getBySocialIdAndType(command.socialId(), command.socialType())
            .map(AuthUser::getUserId)
            .orElseGet(() -> createNewUser(command));
    }

    public Long issueToken(IssueTokenCommand command) {
        Token token = Token.create(command.userId());
        return tokenRepositoryPort.save(token).getId();
    }

    public Long reissueToken(ReissueTokenCommand command) {
        Token token = tokenRepositoryPort.getByUserId(command.userId());
        token.validateUserId(command.userId());
        return issueToken(new IssueTokenCommand(command.userId()));
    }

    public void deleteToken(DeleteTokenCommand command) {
        tokenRepositoryPort.deleteByUserId(command.useId());
    }

    private Long createNewUser(SocialLoginCommand command) {
        String name = RandomNameGenerator.generate().value();
        Long userId = userService.create(new CreateUserCommand(name, null));
        AuthUser authUser = AuthUser.createSocial(userId, command.socialId(), command.socialType());
        authUserRepositoryPort.save(authUser);
        return userId;
    }
}
