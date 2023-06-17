package com.swd392.funfundbe.controller.api.exception.custom;

import com.swd392.funfundbe.model.CustomError;

public class CustomBadRequestException extends BaseCustomException {

    public CustomBadRequestException(CustomError customerError) {
        super(customerError);
    }

}
