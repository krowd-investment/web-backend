package com.swd392.funfundbe.controller;

import com.swd392.funfundbe.controller.api.AuthenticateController;
import com.swd392.funfundbe.model.Response.AuthenticateResponse;
import com.swd392.funfundbe.service.AuthenticateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticateControllerImpl implements AuthenticateController {
    private final AuthenticateService authenticateService;
    @Override
    public ResponseEntity<AuthenticateResponse> authenticateUser() {
        AuthenticateResponse authenticateResponse = authenticateService.authenticateUser();
        return new ResponseEntity<>(
                authenticateResponse, HttpStatus.OK
        );
    }
}
