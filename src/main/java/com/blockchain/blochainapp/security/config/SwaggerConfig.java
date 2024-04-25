package com.blockchain.blochainapp.security.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "blockchain",
                description = "blockchain api"
        )
)
public class SwaggerConfig {
}
