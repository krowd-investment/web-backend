package com.swd392.funfundbe.controller;

import com.swd392.funfundbe.controller.api.UserController;
import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Request.RegisterUserRequest;
import com.swd392.funfundbe.model.Response.UserResponse;
import com.swd392.funfundbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> registerUser(RegisterUserRequest request) throws CustomBadRequestException, CustomNotFoundException {
        UserResponse userResponse = userService.registerUser(request);
        return new ResponseEntity<>(
                userResponse, HttpStatus.OK);
    }
}
