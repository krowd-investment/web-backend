package com.swd392.funfundbe.controller.api.exception.custom;

import com.swd392.funfundbe.model.CustomError;

public class CustomForbiddenException extends BaseCustomException {

    public CustomForbiddenException(CustomError customerError) {
        super(customerError);
    }
}
