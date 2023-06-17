package com.swd392.funfundbe.controller.api.exception.custom;

import com.swd392.funfundbe.model.CustomError;

public class CustomInternalServerException extends BaseCustomException {

    public CustomInternalServerException(CustomError customerError) {
        super(customerError);
    }

}
