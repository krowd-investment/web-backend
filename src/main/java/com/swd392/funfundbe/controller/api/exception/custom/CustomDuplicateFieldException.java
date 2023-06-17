package com.swd392.funfundbe.controller.api.exception.custom;

import com.swd392.funfundbe.model.CustomError;

public class CustomDuplicateFieldException extends BaseCustomException {

    public CustomDuplicateFieldException(CustomError customerError) {
        super(customerError);
    }

}
