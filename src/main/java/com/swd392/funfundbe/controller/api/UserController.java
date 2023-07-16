package com.swd392.funfundbe.controller.api;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Request.NotificationRequest;
import com.swd392.funfundbe.model.Request.PersonalWalletTransactionRequest;
import com.swd392.funfundbe.model.Request.RegisterUserRequest;
import com.swd392.funfundbe.model.Response.PersonalWalletResponse;
import com.swd392.funfundbe.model.Response.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.math.BigDecimal;

@RequestMapping("/api/user")
@Tag(name = "user")
public interface UserController {
        @Operation(summary = "Register user for first time login", description = "Register user for first time login")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Register user successfully")
        })
        @PutMapping
        public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserRequest request)
                        throws CustomBadRequestException, CustomNotFoundException;

        @Operation(summary = "Get current logged in user information", description = "Get current logged in user information")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Get information successfully")
        })
        @GetMapping
        public ResponseEntity<UserResponse> getCurrentUserInfo()
                        throws CustomNotFoundException, CustomForbiddenException;

        @Operation(summary = "Update current logged in user information", description = "Update current logged in user information")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Update information successfully")
        })
        @PutMapping("/update")
        public ResponseEntity<UserResponse> updateCurrentUser(@RequestBody RegisterUserRequest request)
                        throws CustomForbiddenException, CustomNotFoundException;

        // View GeneralWallet
        @Operation(summary = "View general wallet", description = "View general wallet")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Get general wallet successfully")
        })
        @GetMapping("/general-wallet")
        public ResponseEntity<PersonalWalletResponse> getGeneralWallet()
                        throws CustomForbiddenException, CustomNotFoundException;

        // View CollectionWallet
        @Operation(summary = "View collection wallet", description = "View collection wallet")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Get collection wallet successfully")
        })
        @GetMapping("/collection-wallet")
        public ResponseEntity<PersonalWalletResponse> getCollectionWallet()
                        throws CustomNotFoundException, CustomForbiddenException;

        // Deposit money to General Wallet
        @Operation(summary = "Deposit to general wallet by Momo", description = "Deposit to general wallet by Momo")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Call Momo payment successfully")
        })
        @PutMapping("/deposit")
        public ResponseEntity<?> depositToGeneralWallet(
                @RequestParam("platform") @Schema(description = "WEB or MOBILE", example = "WEB") String platform,
                @RequestParam("amount") BigDecimal amount
        ) throws CustomNotFoundException, CustomBadRequestException, CustomForbiddenException;

        // Withdraw money from General Wallet or Collection Wallet
        @PutMapping("/withdraw")
        public ResponseEntity<?> withdrawMoney();

        // Transfer money between personal wallets
        @Operation(summary = "Transfer money between personal wallets", description = "Transfer money between personal wallets")
        @PutMapping("/transfer-money")
        public ResponseEntity<?> transferMoney(@RequestBody @Valid PersonalWalletTransactionRequest request) throws CustomNotFoundException, CustomBadRequestException, CustomForbiddenException;

        @Operation(summary = "logout")
        @PostMapping("/logout")
        public ResponseEntity<?> logout();

        @Operation(summary = "send notification")
        @PostMapping("/sendNotification")
        public ResponseEntity<?> sendNotification(@RequestBody NotificationRequest notificationRequest);
}
