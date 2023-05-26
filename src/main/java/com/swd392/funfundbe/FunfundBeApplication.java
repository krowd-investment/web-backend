package com.swd392.funfundbe;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(
		name = "token_auth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT"
)
@OpenAPIDefinition(
		info = @Info(
				title = "API Doc for Krowd Investment FunFund Web Service",
				description = "This is list of endpoints and documentations of REST API for FunFund Web Service",
				version = "1.0"
		),
		servers = {
			@Server(url = "http://localhost:8080", description = "Local development server domain"),
			@Server(url = "https://localhost:8080", description = "Local development server domain with SSL/TLS")
		},
		security = {
				@SecurityRequirement(name = "token_auth")
		},
		tags = {
				@Tag(name = "welcome", description = "REST API endpoint for welcome user")
		}
)
public class FunfundBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FunfundBeApplication.class, args);
	}

}
