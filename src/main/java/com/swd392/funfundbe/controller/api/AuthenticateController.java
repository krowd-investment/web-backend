package com.swd392.funfundbe.controller.api;

import com.swd392.funfundbe.model.Response.AuthenticateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/authenticate")
public interface AuthenticateController {
    @GetMapping
    public ResponseEntity<AuthenticateResponse> authenticateUser();
}
