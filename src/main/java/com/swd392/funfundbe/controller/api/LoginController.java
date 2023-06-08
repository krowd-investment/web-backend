package com.swd392.funfundbe.controller.api;

import com.swd392.funfundbe.model.Request.LoginRequest;
import com.swd392.funfundbe.model.Response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/login")
public interface LoginController {
    @PostMapping("/google")
    public ResponseEntity<LoginResponse> authenticateGoogle(@RequestBody LoginRequest request);
}
