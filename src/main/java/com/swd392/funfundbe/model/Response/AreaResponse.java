package com.swd392.funfundbe.model.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AreaResponse {
    private int areaId;
    private String city;
    private String district;
    private String details;
    private boolean status;
}
