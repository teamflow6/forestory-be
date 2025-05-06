package com.teamflow.forestory_be.auth.domain.vo;

public sealed interface AuthMethod permits EmailAuth, SocialAuth {
}
