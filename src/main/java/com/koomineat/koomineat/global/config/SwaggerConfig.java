package com.koomineat.koomineat.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("KoominEat API")
                        .description("국민대 음식점/카페 픽업 및 전달 플랫폼 API")
                        .version("v1.0")
                )
                .addSecurityItem(new SecurityRequirement().addList("cookieAuth"))
                .components(new Components().addSecuritySchemes("cookieAuth",
                        new SecurityScheme()
                                .name("cookieAuth")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)
                                .name("AUTH_TOKEN")
                ));
    }
}