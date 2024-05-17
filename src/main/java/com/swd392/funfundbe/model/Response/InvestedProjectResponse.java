package com.swd392.funfundbe.model.Response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class InvestedProjectResponse {
    private UUID investmentId;
    private int projectId;
    private String projectName;
    private BigDecimal mulplier;
    private String image;
    private BigDecimal totalMoney;
    private Date createAt;
    private Date updateAt;
    private String status;
    private BigDecimal shareAmount;
}
