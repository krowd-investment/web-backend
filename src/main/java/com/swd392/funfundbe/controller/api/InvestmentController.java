package com.swd392.funfundbe.controller.api;

import java.util.List;
import java.util.UUID;

import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Request.InvestProjectRequest;
import com.swd392.funfundbe.model.Response.InvestedProjectResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api/investments")
@Tag(name = "investment")
public interface InvestmentController {
    @Operation(summary = "Get all investment current user invested")
    @GetMapping("/")
    public ResponseEntity<List<InvestedProjectResponse>> getAllInvestmentOfCurrentUser() throws CustomNotFoundException, CustomForbiddenException;

    @Operation(summary = "Invest a project with current user")
    @PostMapping("/invest")
    public ResponseEntity<InvestedProjectResponse> investProject(@RequestBody @Valid InvestProjectRequest request)
            throws CustomNotFoundException, CustomForbiddenException, CustomBadRequestException;

    @Operation(summary = "cancel a project")
    @PutMapping("{id}/cancel")
    public ResponseEntity<?> cancelProject(@PathVariable("id") UUID id)
            throws CustomForbiddenException, CustomNotFoundException;

}
