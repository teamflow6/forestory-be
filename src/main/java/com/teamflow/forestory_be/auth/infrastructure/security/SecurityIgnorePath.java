package com.teamflow.forestory_be.auth.infrastructure.security;

import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public final class SecurityIgnorePath {

    public static final OrRequestMatcher IGNORE_REQUEST_MATCHER = initIgnorePaths();

    private SecurityIgnorePath() {
    }

    private static OrRequestMatcher initIgnorePaths() {
        List<RequestMatcher> matchers = List.of(
            // User
            new RegexRequestMatcher("/api/v1/users/\\d+$", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/users/name/exists", HttpMethod.GET.name()),

            // Auth
            new AntPathRequestMatcher("/api/v1/auth/reissue", HttpMethod.POST.name()),

            // Article
            new AntPathRequestMatcher("/api/v1/articles/**")
        );
        return new OrRequestMatcher(matchers);
    }
}
