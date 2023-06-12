package com.swd392.funfundbe;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
				@Tag(name = "welcome", description = "REST API endpoint for welcome user")
		}
)
@Slf4j
public class FunfundBeApplication {

	public static void main(String[] args) {
		try {
			FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build();
			FirebaseApp app = FirebaseApp.getApps().isEmpty()
					? FirebaseApp.initializeApp(options)
					: FirebaseApp.getInstance();
//			FirebaseApp.initializeApp(options, "FunFundFirebaseApp");
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
		SpringApplication.run(FunfundBeApplication.class, args);
	}

}
