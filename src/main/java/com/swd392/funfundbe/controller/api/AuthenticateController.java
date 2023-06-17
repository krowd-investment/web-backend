package com.swd392.funfundbe.controller.api;

import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Response.AuthenticateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/authenticate")
public interface AuthenticateController {
    @Operation(
            summary = "Authenticate user with firebase id token",
            description = "Authenticate user with firebase id token",
            tags = "authenticate"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authenticate user successfully")
    })
    @GetMapping
    public ResponseEntity<AuthenticateResponse> authenticateUser() throws CustomNotFoundException;
}
