package com.swd392.funfundbe.model.Response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private int projectId;
    private String projectName;
    private String image;
    private BigDecimal targetCapital;
    private BigDecimal sharedRevenue;
    private BigDecimal mutiplier;
    private int duration;
    private BigDecimal capital;
    private String brand;
    private Date startDate;
    private Date endDate;
    private String status;
    private int numberOfStage;
    private String projectDescription;
    private int areaId;
    private String areaName;
    private int fieldId;
    private String fieldName;
    private BigDecimal investmentTargetCapital;
    private BigDecimal paidAmount;
    private BigDecimal remainingAmount;
    private String businessLicense;
}
