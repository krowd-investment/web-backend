package com.swd392.funfundbe.controller;

import com.swd392.funfundbe.controller.api.LoginController;
import com.swd392.funfundbe.model.Request.LoginRequest;
import com.swd392.funfundbe.model.Response.LoginResponse;
import com.swd392.funfundbe.service.LoginService;
import com.twilio.Twilio;
import com.twilio.http.Response;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginControllerImpl implements LoginController {
    private final LoginService loginService;

    @Override
    public ResponseEntity<LoginResponse> authenticateGoogle(LoginRequest request) {
        LoginResponse loginResponse = loginService.authenticateGoogle(request.getIdToken());
        return new ResponseEntity<>(
                loginResponse, HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<String> generateOTPForPhone(LoginRequest request) {
        loginService.generateOtpForPhone(request.getPhone());
        return new ResponseEntity<>(
                "Your OTP has been sent to your verified phone number",
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<LoginResponse> verifyOtpForPhone(LoginRequest request) {
        LoginResponse loginResponse = loginService.verifyOtpForPhone(request);
        return new ResponseEntity<>(
                loginResponse, HttpStatus.OK
        );
    }


}
