package com.campus.clean.arc.application.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(@Value("${foodtechlab.build.version}") String version) {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("apiKeyAuth",
                        new SecurityScheme().type(SecurityScheme.Type.APIKEY).scheme("apiKey")
                                .in(SecurityScheme.In.HEADER).name("X-Auth-Token")))
                .info(new Info()
                        .title("API Service")
                        .version(version)
                        .description("Документация"))
                .addServersItem(new Server().url("/"))
                .addSecurityItem(new SecurityRequirement().addList("apiKeyAuth", Arrays.asList("read", "write")));
    }

    @Bean
    @Primary
    public SwaggerUiConfigProperties swaggerUiProperties(SwaggerUiConfigProperties configProperties) {
        configProperties.setDocExpansion("none");
        configProperties.setUseRootPath(true);
        return configProperties;
    }

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("ALL")
                .pathsToMatch("/**")
                .packagesToScan("ru")
                .build();
    }
}
