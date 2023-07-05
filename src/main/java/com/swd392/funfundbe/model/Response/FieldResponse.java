package com.swd392.funfundbe.model.Response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FieldResponse {
    private int fieldId;
    private String name;
    private String fieldDescription;
    private boolean status;
}
