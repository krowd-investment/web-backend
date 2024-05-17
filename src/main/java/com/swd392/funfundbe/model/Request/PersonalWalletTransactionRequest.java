package com.swd392.funfundbe.model.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class PersonalWalletTransactionRequest {
    @NotNull
    @Schema(example = "COLLECTION_WALLET")
    private String fromWallet;
    @NotNull
    @Schema(example = "GENERAL_WALLET")
    private String toWallet;
    @NotNull
    private BigDecimal amount;
    private String description;
}
