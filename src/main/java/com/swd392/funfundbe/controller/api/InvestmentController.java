package com.swd392.funfundbe.controller.api;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swd392.funfundbe.model.Request.InvestProjectRequest;
import com.swd392.funfundbe.model.Response.InvestedProjectResponse;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/api/investments")
@Tag(name = "investment")
public interface InvestmentController {
    @Operation(summary = "Get all investment user invested")
    @GetMapping("/{userId}")
    public ResponseEntity<List<InvestedProjectResponse>> getAllInvestmentByUserId(@PathVariable("userId") int id);

    @Operation(summary = "invest a project")
    @PostMapping("/invest")
    public ResponseEntity<InvestedProjectResponse> investProject(@RequestBody InvestProjectRequest request);

    @Operation(summary = "invest a project")
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelProject(@RequestBody InvestProjectRequest request);

}
