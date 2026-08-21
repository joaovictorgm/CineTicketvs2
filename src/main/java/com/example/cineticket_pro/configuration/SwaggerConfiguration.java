package com.example.cineticket_pro.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    public OpenAPI customOpemAPI(){

        return new OpenAPI().info(new Info().title("cineticket").version("1.0.0").description("Api para projeto do cineticket"));
    }
}
