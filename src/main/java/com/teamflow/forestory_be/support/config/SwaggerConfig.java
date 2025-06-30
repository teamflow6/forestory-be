package com.teamflow.forestory_be.support.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        Info info = new Info()
            .title("Sooptory API 문서")
            .version("v1.0")
            .description("Sooptory 프로젝트의 백엔드 API 명세입니다.");

        SecurityScheme accessTokenScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .name("Authorization");

        SecurityScheme idTokenScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .name("Authorization");

        Components components = new Components()
            .addSecuritySchemes("AccessToken", accessTokenScheme)
            .addSecuritySchemes("IdToken", idTokenScheme);

        return new OpenAPI()
            .components(components)
            .addServersItem(new Server().url("/"))
            .info(info);
    }
}
