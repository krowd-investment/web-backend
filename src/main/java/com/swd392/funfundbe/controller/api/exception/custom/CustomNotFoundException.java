package com.swd392.funfundbe.controller.api.exception.custom;

import com.swd392.funfundbe.model.CustomError;

public class CustomNotFoundException extends BaseCustomException {

    public CustomNotFoundException(CustomError customerError) {
        super(customerError);
    }

}
