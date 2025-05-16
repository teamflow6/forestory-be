package com.teamflow.forestory_be.user.presentation;

import com.teamflow.forestory_be.user.presentation.dto.request.UpdateProfileRequest;
import com.teamflow.forestory_be.user.presentation.dto.request.UserOnboardingRequest;
import com.teamflow.forestory_be.user.presentation.dto.response.UserNameExistsResponse;
import com.teamflow.forestory_be.user.presentation.dto.response.UserProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "유저 API", description = "사용자 프로필 관련 API")
@RequestMapping("/api/v1/users")
public interface UserApi {

    @Operation(summary = "내 프로필 조회", security = @SecurityRequirement(name = "JWT"))
    @GetMapping("/me")
    ResponseEntity<UserProfileResponse> getMyProfile(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId
    );

    @Operation(summary = "다른 유저 프로필 조회")
    @GetMapping("/{userId}")
    ResponseEntity<UserProfileResponse> getUserProfile(
        @PathVariable Long userId
    );

    @Operation(summary = "온보딩 완료", security = @SecurityRequirement(name = "JWT"))
    @PutMapping("/profile")
    ResponseEntity<UserProfileResponse> completeOnboarding(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @RequestBody UserOnboardingRequest request
    );

    @Operation(summary = "프로필 수정", security = @SecurityRequirement(name = "JWT"))
    @PatchMapping("/profile")
    ResponseEntity<UserProfileResponse> update(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @RequestBody UpdateProfileRequest request
    );

    @Operation(summary = "닉네임 중복 확인")
    @GetMapping("/name/exists")
    ResponseEntity<UserNameExistsResponse> checkNameExists(
        @RequestParam String name
    );
}
