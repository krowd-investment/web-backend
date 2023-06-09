package com.swd392.funfundbe.controller.api;

import com.swd392.funfundbe.model.Request.LoginRequest;
import com.swd392.funfundbe.model.Response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/login")
public interface LoginController {
    @PostMapping("/google")
    public ResponseEntity<LoginResponse> authenticateGoogle(@RequestBody LoginRequest request);

    @PostMapping("/phone/generateOtp")
    public ResponseEntity<String> generateOTPForPhone(@RequestBody LoginRequest request);

    @PostMapping("/phone/verifyOtp")
    public ResponseEntity<LoginResponse> verifyOtpForPhone(@RequestBody LoginRequest request);
}
