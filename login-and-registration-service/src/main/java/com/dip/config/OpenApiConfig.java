package com.dip.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@OpenAPIDefinition (
		info = @Info(
				title = "Login And Registration Service",
				description = "Jwt Authentication Microservice : It saves iser in the repository and retrives user form the respsitory upon authentication",
				version = "v1"
				),
		servers = {
				@Server(
						description = "Dev",
						url = "http://localhost:8081"
						)
		},
		security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(
				name = "AuthSecurityScheme"
				)
		)
public class OpenApiConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new io.swagger.v3.oas.models.info.Info().title("Login And Registration Service"))				
				.addSecurityItem(new SecurityRequirement().addList("AuthSecurityScheme"))
				.components(new Components().addSecuritySchemes("AuthSecurityScheme", new SecurityScheme()
						.name("AuthSecurityScheme").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
	}

}
