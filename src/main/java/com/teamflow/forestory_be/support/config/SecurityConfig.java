package com.teamflow.forestory_be.support.config;

import com.teamflow.forestory_be.auth.infrastructure.security.JwtAuthenticationFilter;
import com.teamflow.forestory_be.auth.infrastructure.security.SecurityIgnorePath;
import com.teamflow.forestory_be.auth.infrastructure.security.oauth.CookieOAuth2RequestRepository;
import com.teamflow.forestory_be.auth.infrastructure.security.oauth.CustomOAuth2UserService;
import com.teamflow.forestory_be.auth.infrastructure.security.oauth.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String AUTHORIZATION_REQUEST_URI = "/api/v1/oauth2/authorization";
    private static final String AUTHORIZATION_RESPONSE_URI = "/api/v1/oauth2/*/login";

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final CookieOAuth2RequestRepository cookieOAuth2RequestRepository;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(SecurityIgnorePath.IGNORE_REQUEST_MATCHER).permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(config ->
                    config.baseUri(AUTHORIZATION_REQUEST_URI)
                )
                .redirectionEndpoint(config ->
                    config.baseUri(AUTHORIZATION_RESPONSE_URI)
                )
                .authorizationEndpoint(config ->
                    config.authorizationRequestRepository(cookieOAuth2RequestRepository))
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)
                )
                .successHandler(successHandler)
            );
        return http.build();
    }
}
