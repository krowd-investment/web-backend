package com.swd392.funfundbe.controller;

import com.google.api.Http;
import com.swd392.funfundbe.controller.api.AdminController;
import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Response.UserResponse;
import com.swd392.funfundbe.service.AdminService;
import com.swd392.funfundbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {
    private final AdminService adminService;
    private final UserService userService;

    @Override
    public ResponseEntity<?> approvePO(Integer poId) throws CustomNotFoundException, CustomBadRequestException {
        adminService.approvePO(poId);
        return new ResponseEntity<>("Approve successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> rejectPO(Integer poId) throws CustomNotFoundException, CustomBadRequestException {
        adminService.rejectPO(poId);
        return new ResponseEntity<>("Reject successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> list = userService.getAllUsers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> approveProject(Integer projectId) throws CustomNotFoundException, CustomBadRequestException {
        adminService.approveProject(projectId);
        return new ResponseEntity<>("Approve project successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> rejectProject(Integer projectId) throws CustomNotFoundException, CustomBadRequestException {
        adminService.rejectProject(projectId);
        return new ResponseEntity<>("Reject project successfully", HttpStatus.OK);
    }
}
