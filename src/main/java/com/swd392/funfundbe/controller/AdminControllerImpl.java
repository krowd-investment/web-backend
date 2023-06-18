package com.swd392.funfundbe.controller;

import com.swd392.funfundbe.controller.api.AdminController;
import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {
    private final AdminService adminService;

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
}