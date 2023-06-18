package com.swd392.funfundbe.controller.api;

import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Request.PersonalWalletTransactionRequest;
import com.swd392.funfundbe.model.Request.RegisterUserRequest;
import com.swd392.funfundbe.model.Response.PersonalWalletResponse;
import com.swd392.funfundbe.model.Response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@Tag(name = "user")
public interface UserController {
    @Operation(
            summary = "Register user for first time login",
            description = "Register user for first time login"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register user successfully")
    })
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserRequest request) throws CustomBadRequestException, CustomNotFoundException;

    @Operation(
            summary = "Get current logged in user information",
            description = "Get current logged in user information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get information successfully")
    })
    @GetMapping
    public ResponseEntity<UserResponse> getCurrentUserInfo() throws CustomNotFoundException, CustomForbiddenException;

    //View GeneralWallet
    @Operation(
            summary = "View general wallet",
            description = "View general wallet"
    )
    @GetMapping("/general-wallet")
    public ResponseEntity<PersonalWalletResponse> getGeneralWallet();

    //View CollectionWallet
    @Operation(
            summary = "View collection wallet",
            description = "View collection wallet"
    )
    @GetMapping("/collection-wallet")
    public ResponseEntity<PersonalWalletResponse> getCollectionWallet();

    //Deposit money to General Wallet
    @PutMapping("/deposit")
    public ResponseEntity<?> depositToGeneralWallet();

    // Withdraw money from General Wallet or Collection Wallet
    @PutMapping("/withdraw")
    public ResponseEntity<?> withdrawMoney();

    //Transfer money between personal wallets
    @Operation(
            summary = "Transfer money between personal wallets",
            description = "Transfer money between personal wallets"
    )
    @PutMapping("/transfer-money")
    public ResponseEntity<?> transferMoney(@RequestBody @Valid PersonalWalletTransactionRequest request);
}
