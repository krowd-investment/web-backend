package com.swd392.funfundbe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.swd392.funfundbe.controller.api.InvestmentController;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Request.InvestProjectRequest;
import com.swd392.funfundbe.model.Response.InvestedProjectResponse;
import com.swd392.funfundbe.service.investment.InvestmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InvestmentControllerImpl implements InvestmentController {
    private final InvestmentService investmentService;

    @Override
    public ResponseEntity<List<InvestedProjectResponse>> getAllInvestmentOfCurrentUser()
            throws CustomNotFoundException {
        List<InvestedProjectResponse> responses = investmentService.getAllInvestmentOfCurrentUser();
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<InvestedProjectResponse> investProject(InvestProjectRequest request)
            throws CustomNotFoundException, CustomForbiddenException {
        InvestedProjectResponse investedProjectResponse = investmentService.investProject(request);
        return ResponseEntity.ok(investedProjectResponse);
    }

    @Override
    public ResponseEntity<String> cancelProject(InvestProjectRequest request) {
        return ResponseEntity.ok("Cancel Project successfully");
    }

}
