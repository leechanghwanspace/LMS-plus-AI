package com.project.LMS_plus.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SwaggerConfig {

    @Bean
    @Profile("!Prod")
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Telemedicine Web Services")
                        .description("Backend API documentation")
                        .version("1.0"));
    }
}