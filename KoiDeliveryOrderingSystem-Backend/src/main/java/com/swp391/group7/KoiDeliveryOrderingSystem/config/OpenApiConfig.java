package com.swp391.group7.KoiDeliveryOrderingSystem.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Koi Delivery Ordering System API")
                        .version("1.0")
                        .description("API for Koi Delivery Ordering System"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .tags(List.of(
                        new Tag().name("Auth"),
                        new Tag().name("Order"),
                        new Tag().name("Fish Profile"),
                        new Tag().name("Certificate"),
                        new Tag().name("Health Service Order"),
                        new Tag().name("Customs Declaration"),
                        new Tag().name("Payment"),
                        new Tag().name("Handover Document"),
                        new Tag().name("Checking Koi Health"),
                        new Tag().name("Package"),
                        new Tag().name("Health Care Delivery History"),
                        new Tag().name("Report"),
                        new Tag().name("Feedback"),
                        new Tag().name("Customer"),
                        new Tag().name("Fish Category"),
                        new Tag().name("Delivery Method"),
                        new Tag().name("Health Service Category"),
                        new Tag().name("Dashboard")
                ));
    }
}
