package com.swd392.funfundbe.model.Request;

import java.math.BigDecimal;
import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swd392.funfundbe.model.entity.Project;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaFilterRequest {
    private String areaName;
}
