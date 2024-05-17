package com.swd392.funfundbe.model.Request;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateProjectRequest {
    private int fieldId;
    private int areaId;
    @NotBlank
    private String projectName;
    @NotBlank
    private String brand;
    @Positive
    @NotNull
    private BigDecimal investmentTargetCapital;
    @Positive
    @NotNull
    private BigDecimal sharedRevenue;
    @Positive
    @NotNull
    private BigDecimal multiplier;
    private int duration;
    private Date startDate;
    private Date endDate;
    private String image;
    private String projectDescription;
    @NotBlank
    private String businessLicense;
    @Positive
    private int numberOfStage;
}
