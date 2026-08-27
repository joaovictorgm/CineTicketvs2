package com.example.cineticket_pro.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// link swaager http://localhost:8080/swagger-ui.html

@Configuration
public class SwaggerConfiguration {
@Bean
    public OpenAPI customOpemAPI(){

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")).components(new Components().addSecuritySchemes("bearerAuth",new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").scheme("bearer").bearerFormat("JWT")
                )).info(new Info().title("cineticket").version("1.0.0").description("Api para projeto do cineticket"));
    }
}
