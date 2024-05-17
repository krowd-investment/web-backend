package com.swd392.funfundbe.controller;

import org.springframework.web.bind.annotation.RestController;

import com.swd392.funfundbe.controller.api.LoginController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginControllerImpl implements LoginController {
    // private final LoginService loginService;
    //
    // @Override
    // public ResponseEntity<LoginResponse> authenticateGoogle(LoginRequest request)
    // {
    // LoginResponse loginResponse =
    // loginService.authenticateGoogle(request.getIdToken());
    // return new ResponseEntity<>(
    // loginResponse, HttpStatus.OK
    // );
    // }
    //
    // @Override
    // public ResponseEntity<String> generateOTPForPhone(LoginRequest request) {
    // loginService.generateOtpForPhone(request.getPhone());
    // return new ResponseEntity<>(
    // "Your OTP has been sent to your verified phone number",
    // HttpStatus.OK
    // );
    // }
    //
    // @Override
    // public ResponseEntity<LoginResponse> verifyOtpForPhone(LoginRequest request)
    // {
    // LoginResponse loginResponse = loginService.verifyOtpForPhone(request);
    // return new ResponseEntity<>(
    // loginResponse, HttpStatus.OK
    // );
    // }

}
