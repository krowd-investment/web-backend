package com.swd392.funfundbe.model.Response.transaction;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PersonalWalletTransactionHistory {
    private int personalWalletTransactionId;
    private UUID fromWalletId;
    private UUID toWalletId;
    private Date createdAt;
    private BigDecimal amount;
    private BigDecimal fee;
    private String description;
}
