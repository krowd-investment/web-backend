package com.swd392.funfundbe.controller;

import com.swd392.funfundbe.controller.api.POController;
import com.swd392.funfundbe.model.Request.CreateProjectRequest;
import com.swd392.funfundbe.model.Response.PersonalWalletResponse;
import com.swd392.funfundbe.model.Response.ProjectResponse;
import com.swd392.funfundbe.model.Response.ProjectWalletResponse;
import com.swd392.funfundbe.model.enums.WalletType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class POControllerImpl implements POController {

    @Override
    public ResponseEntity<?> createProject(CreateProjectRequest request) {
        return new ResponseEntity<>("Create project successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateProject(int projectId, CreateProjectRequest request) {
        return new ResponseEntity<>("Update project successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteProject(int projectId) {
        return new ResponseEntity<>("Delete project successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> startProject(int projectId) {
        return new ResponseEntity<>("Start project successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProjectWalletResponse> getProjectInvestmentWallet(int projectId) {
        ProjectWalletResponse response = new ProjectWalletResponse(
                UUID.randomUUID(),
                WalletType.PROJECT_INVESTMENT_WALLET.toString(),
                projectId,
                new BigDecimal(2000000000),
                new Date(),
                true
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProjectWalletResponse> getProjectPaymentWallet(int projectId) {
        ProjectWalletResponse response = new ProjectWalletResponse(
                UUID.randomUUID(),
                WalletType.PROJECT_PAYMENT_WALLET.toString(),
                projectId,
                new BigDecimal(2000000000),
                new Date(),
                true
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}