package com.swd392.funfundbe.model.Request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CreateProjectRequest {
    private int fieldId;
    private int areaId;
    private String projectName;
    private String brand;
    private BigDecimal investmentTargetCapital;
    private BigDecimal sharedRevenue;
    private BigDecimal multiplier;
    private int numberOfStage;
    private int duration;
    private Date startDate;
    private Date endDate;
    private String image;
    private String projectDescription;
    private String businessLicense;
}
