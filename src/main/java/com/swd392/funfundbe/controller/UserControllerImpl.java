package com.swd392.funfundbe.controller;

import com.swd392.funfundbe.controller.api.UserController;
import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Request.NotificationRequest;
import com.swd392.funfundbe.model.Request.PersonalWalletTransactionRequest;
import com.swd392.funfundbe.model.Request.RegisterUserRequest;
import com.swd392.funfundbe.model.Response.PersonalWalletResponse;
import com.swd392.funfundbe.model.Response.UserResponse;
import com.swd392.funfundbe.model.enums.WalletType;
import com.swd392.funfundbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> registerUser(RegisterUserRequest request)
            throws CustomBadRequestException, CustomNotFoundException {
        UserResponse userResponse = userService.registerUser(request);
        return new ResponseEntity<>(
                userResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserResponse> getCurrentUserInfo() throws CustomNotFoundException, CustomForbiddenException {
        UserResponse userResponse = userService.getCurrentUserInfo();
        return new ResponseEntity<>(
                userResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PersonalWalletResponse> getGeneralWallet() {
        PersonalWalletResponse response = new PersonalWalletResponse(
                UUID.randomUUID(),
                20,
                WalletType.GENERAL_WALLET.toString(),
                new BigDecimal(2000000),
                new Date());
        return new ResponseEntity<>(
                response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PersonalWalletResponse> getCollectionWallet() {
        PersonalWalletResponse response = new PersonalWalletResponse(
                UUID.randomUUID(),
                20,
                WalletType.COLLECTION_WALLET.toString(),
                new BigDecimal(2000000),
                new Date());
        return new ResponseEntity<>(
                response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> depositToGeneralWallet() {
        return new ResponseEntity<>("Under development", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> withdrawMoney() {
        return new ResponseEntity<>("Under development", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> transferMoney(PersonalWalletTransactionRequest request) {
        return new ResponseEntity<>("Transfer money successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("log out successfully");
    }

    @Override
    public ResponseEntity<?> sendNotification(NotificationRequest notificationRequest) {
        return ResponseEntity.ok("Send Notification Successfully");
    }
}
