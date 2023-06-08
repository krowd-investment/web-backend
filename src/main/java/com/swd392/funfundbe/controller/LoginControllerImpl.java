package com.swd392.funfundbe.controller;

import com.swd392.funfundbe.controller.api.LoginController;
import com.swd392.funfundbe.model.Request.LoginRequest;
import com.swd392.funfundbe.model.Response.LoginResponse;
import com.swd392.funfundbe.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginControllerImpl implements LoginController {

    private final LoginService loginService;

    @Override
    public ResponseEntity<LoginResponse> authenticateGoogle(LoginRequest request) {
        LoginResponse loginResponse = loginService.authenticateGoogle(request.getIdToken());
        return new ResponseEntity<>(
                loginResponse, HttpStatus.OK
        );
    }
}
