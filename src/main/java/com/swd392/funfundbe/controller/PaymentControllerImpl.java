package com.swd392.funfundbe.controller;

import com.swd392.funfundbe.config.MomoConfig;
import com.swd392.funfundbe.controller.api.PaymentController;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.momo.MomoPaymentResult;
import com.swd392.funfundbe.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {

    private final String SUCCESS_MESSAGE = "Deposit to FunFund platform successfully";
    private final MomoConfig momoConfig;
    private final PaymentService paymentService;

    @Override
    public String redirectAfterMomoPayment() {
        System.out.println(momoConfig);
        return SUCCESS_MESSAGE;
    }

    @Override
    public void receiveResultFromMomo(MomoPaymentResult result) throws CustomNotFoundException, CustomForbiddenException {
        paymentService.verifyDepositTransaction(result);
    }
}
