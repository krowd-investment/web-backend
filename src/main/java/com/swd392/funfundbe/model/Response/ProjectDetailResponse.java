package com.swd392.funfundbe.model.Response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDetailResponse {
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
}
