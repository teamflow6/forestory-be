package com.teamflow.forestory_be.user.presentation;

import com.teamflow.forestory_be.user.application.dto.CheckNameExistsQuery;
import com.teamflow.forestory_be.user.application.dto.OnboardingUserCommand;
import com.teamflow.forestory_be.user.application.dto.ReadUserQuery;
import com.teamflow.forestory_be.user.application.dto.UpdateUserCommand;
import com.teamflow.forestory_be.user.application.service.UserService;
import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.presentation.dto.request.UpdateProfileRequest;
import com.teamflow.forestory_be.user.presentation.dto.request.UserOnboardingRequest;
import com.teamflow.forestory_be.user.presentation.dto.response.UserNameExistsResponse;
import com.teamflow.forestory_be.user.presentation.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(
        @AuthenticationPrincipal Long userId
    ) {
        ReadUserQuery query = new ReadUserQuery(userId);
        User user = userService.getUserById(query);
        UserProfileResponse response = UserProfileResponse.from(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfile(
        @PathVariable Long userId
    ) {
        ReadUserQuery query = new ReadUserQuery(userId);
        User user = userService.getUserById(query);
        UserProfileResponse response = UserProfileResponse.from(user);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> completeOnboarding(
        @AuthenticationPrincipal Long userId,
        @RequestBody UserOnboardingRequest request
    ) {
        OnboardingUserCommand command = new OnboardingUserCommand(
            userId,
            request.name(),
            request.profileUrl()
        );
        User user = userService.completeOnboarding(command);
        UserProfileResponse response = UserProfileResponse.from(user);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/profile")
    public ResponseEntity<UserProfileResponse> update(
        @AuthenticationPrincipal Long userId,
        @RequestBody UpdateProfileRequest request
    ) {
        UpdateUserCommand command = new UpdateUserCommand(
            userId,
            request.name(),
            request.profileUrl()
        );
        User user = userService.update(command);
        UserProfileResponse response = UserProfileResponse.from(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/exists")
    public ResponseEntity<UserNameExistsResponse> checkNameExists(
        @RequestParam String name
    ) {
        CheckNameExistsQuery query = new CheckNameExistsQuery(name);
        boolean exists = userService.existsByName(query);
        UserNameExistsResponse response = new UserNameExistsResponse(exists);
        return ResponseEntity.ok(response);
    }
}
