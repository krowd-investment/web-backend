package com.swd392.funfundbe.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1")
@Tag(name = "welcome")
public interface WelcomeController {

        @Operation(summary = "Welcome user with username", description = "Welcome user with username")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Access api successfully")
        })
        @GetMapping("/welcome")
        public String welcomeUser(@RequestParam(name = "username") String username);
}
