package com.swd392.funfundbe.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.swd392.funfundbe.controller.api.InvestmentController;
import com.swd392.funfundbe.model.Request.InvestProjectRequest;
import com.swd392.funfundbe.model.Response.InvestedProjectResponse;

@RestController
public class InvestmentControllerImpl implements InvestmentController {

    @Override
    public ResponseEntity<List<InvestedProjectResponse>> getAllInvestmentByUserId(int id) {
        Date createDate = new Date();
        BigDecimal x = new BigDecimal(100);
        BigDecimal y = new BigDecimal(1.4);
        List<InvestedProjectResponse> investedProjectResponse = new ArrayList<>();
        investedProjectResponse
                .add(new InvestedProjectResponse(1, "Passio", y, "image.png", x, createDate, createDate, "SUCCESSS"));
        investedProjectResponse
                .add(new InvestedProjectResponse(2, "Cafe", y, "image.png", x, createDate, createDate, "SUCCESSS"));
        return ResponseEntity.ok(investedProjectResponse);
    }

    @Override
    public ResponseEntity<InvestedProjectResponse> investProject(InvestProjectRequest request) {
        Date createDate = new Date();
        BigDecimal x = new BigDecimal(100);
        BigDecimal y = new BigDecimal(1.4);
        return ResponseEntity
                .ok(new InvestedProjectResponse(1, "Passio", y, "img.png", x, createDate, createDate, "LAUNCHED"));
    }

    @Override
    public ResponseEntity<String> cancelProject(InvestProjectRequest request) {
        return ResponseEntity.ok("Cancel Project successfully");
    }

}
