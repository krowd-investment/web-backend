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
public class InvestmentTransactionHistory {
    private int investmentTransactionId;
    private UUID personalWalletId;
    private UUID projectWalletId;
    private String type;
    private Date createdAt;
    private BigDecimal amount;
    private String description;
}
