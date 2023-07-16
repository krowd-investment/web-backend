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
import com.swd392.funfundbe.model.enums.WalletTypeString;
import com.swd392.funfundbe.service.PaymentService;
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
    private final PaymentService paymentService;

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
    public ResponseEntity<UserResponse> updateCurrentUser(RegisterUserRequest request) throws CustomForbiddenException, CustomNotFoundException {
        UserResponse userResponse = userService.updateCurrentUser(request);
        return new ResponseEntity<>(
                userResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PersonalWalletResponse> getGeneralWallet() throws CustomForbiddenException, CustomNotFoundException {
        PersonalWalletResponse response = userService.getPersonalWallet(WalletTypeString.GENERAL_WALLET);
        return new ResponseEntity<>(
                response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PersonalWalletResponse> getCollectionWallet() throws CustomNotFoundException, CustomForbiddenException {
        PersonalWalletResponse response = userService.getPersonalWallet(WalletTypeString.COLLECTION_WALLET);
        return new ResponseEntity<>(
                response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> depositToGeneralWallet(String platform, BigDecimal amount) throws CustomNotFoundException, CustomBadRequestException, CustomForbiddenException {
        Object response = paymentService.depositToGeneralWallet(platform, amount);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> withdrawMoney() {
        return new ResponseEntity<>("Under development", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> transferMoney(PersonalWalletTransactionRequest request) throws CustomNotFoundException, CustomBadRequestException, CustomForbiddenException {
        userService.transferMoney(request);
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
