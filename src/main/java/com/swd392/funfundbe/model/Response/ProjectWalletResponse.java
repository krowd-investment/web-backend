package com.swd392.funfundbe.model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ProjectWalletResponse {
    private UUID projectWalletId;
    private String walletTypeId;
    private int projectId;
    private BigDecimal balance;
    private Date updatedAt;
    private boolean status;
}
