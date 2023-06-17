package com.swd392.funfundbe.controller.api.exception.custom;

import com.swd392.funfundbe.controller.api.exception.custom.BaseCustomException;
import com.swd392.funfundbe.model.CustomError;

public class CustomUnauthorizedException extends BaseCustomException {

    public CustomUnauthorizedException(CustomError customerError) {
        super(customerError);
    }

}
