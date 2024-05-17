package com.swd392.funfundbe.model.Request;

import java.math.BigDecimal;
import java.util.Date;

import com.google.auto.value.AutoValue.Builder;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InvestProjectRequest {
    private int projectId;
    @NotNull
    @Positive
    private BigDecimal totalMoney;
}
