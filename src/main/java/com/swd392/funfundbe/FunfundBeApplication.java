package com.swd392.funfundbe;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.swd392.funfundbe.service.AuthenticateService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SortComparator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
@SecurityScheme(
		name = "token_auth",
		type = SecuritySchemeType.HTTP,
		in = SecuritySchemeIn.HEADER,
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
		@Server(url = "http://localhost:8080", description = "Local development server domain")
	},
	security = {
			@SecurityRequirement(name = "token_auth")
	},
	tags = {
			@Tag(name = "welcome", description = "REST API endpoints for welcome user"),
			@Tag(name = "authentication", description = "REST API endpoints for authenticate user"),
			@Tag(name = "user", description = "REST API endpoints for user"),
			@Tag(name = "admin", description = "REST API endpoints for admin"),
			@Tag(name = "project owner", description = "REST API endpoints for PO"),
			@Tag(name = "project", description = "REST API endpoints for projects"),
			@Tag(name = "investment", description = "REST API endpoints for invest projects")

	}
)
@Slf4j
public class FunfundBeApplication {

	@Primary
	@Bean
	public void initFirebase() throws IOException {
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(getClass().getClassLoader().getResourceAsStream("serviceAccountKey.json")))
				.build();
		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(FunfundBeApplication.class, args);
	}

}
