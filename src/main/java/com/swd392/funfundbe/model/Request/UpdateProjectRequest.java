package com.swd392.funfundbe.model.Request;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateProjectRequest {
    private int fieldId;
    private int areaId;
    private String projectName;
    private String brand;
    private BigDecimal investmentTargetCapital;
    private BigDecimal sharedRevenue;
    private BigDecimal multiplier;
    private int duration;
    private Date startDate;
    private Date endDate;
    private String image;
    private String projectDescription;
    private String businessLicense;
    private int numberOfStage;
}
