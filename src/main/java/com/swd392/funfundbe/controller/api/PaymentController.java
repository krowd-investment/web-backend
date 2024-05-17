package com.swd392.funfundbe.controller.api;

import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.momo.MomoPaymentResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/payment")
public interface PaymentController {

    @GetMapping("/momo/result")
    public String redirectAfterMomoPayment();

    @PostMapping("/momo/result")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void receiveResultFromMomo(@RequestBody MomoPaymentResult result) throws CustomNotFoundException, CustomForbiddenException;
}
