package com.swd392.funfundbe.controller.api.exception.custom;

import java.util.HashMap;
import java.util.Map;

import com.swd392.funfundbe.model.CustomError;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseCustomException extends Exception {
    protected Map<String, CustomError> errorHashMap;

    public BaseCustomException(CustomError customerError) {
        errorHashMap = new HashMap<>();
        errorHashMap.put("error", customerError);
    }
}
