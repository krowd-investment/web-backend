package com.swd392.funfundbe.controller.api;

import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Request.RegisterUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/user")
public interface UserController {
    @Operation(
            summary = "Register user for first time login",
            description = "Register user for first time login",
            tags = "user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register user successfully")
    })
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserRequest request) throws CustomBadRequestException, CustomNotFoundException;
}
