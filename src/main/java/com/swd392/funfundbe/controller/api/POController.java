package com.swd392.funfundbe.controller.api;

import com.swd392.funfundbe.model.Request.CreateProjectRequest;
import com.swd392.funfundbe.model.Response.PersonalWalletResponse;
import com.swd392.funfundbe.model.Response.ProjectResponse;
import com.swd392.funfundbe.model.Response.ProjectWalletResponse;
import com.twilio.http.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/po")
@Tag(name = "project owner")
public interface POController {

    //Create projects and submit it to admin
    @Operation(
            summary = "PO create project",
            description = "PO create project for investment"
    )
    @PostMapping("/project")
    public ResponseEntity<?> createProject(@RequestBody @Valid CreateProjectRequest request);

    //Update project (only before receiving money)
    @Operation(
            summary = "PO update project",
            description = "PO update project for investment"
    )
    @PutMapping("/project/{project_id}")
    public ResponseEntity<?> updateProject(@PathVariable("project_id") int projectId,
                                           @RequestBody @Valid CreateProjectRequest request);

    //Delete project (only before receiving money)
    @Operation(
            summary = "PO delete project",
            description = "PO delete project, oly available before receiving money"
    )
    @DeleteMapping("/project/{project_id}")
    public ResponseEntity<?> deleteProject(@PathVariable("project_id") int projectId);

    //Start projects
    @Operation(
            summary = "PO start project",
            description = "PO start project after receiving enough capital"
    )
    @PutMapping("/project/start/{project_id}")
    public ResponseEntity<?> startProject(@PathVariable("project_id") int projectId);

    //View all po's projects

    //Filter projects

    //View project details

    //View ProjectInvestmentWallet
    @Operation(
            summary = "PO view project-investment-wallet",
            description = "PO view project-investment-wallet of owned project"
    )
    @GetMapping("project/{project_id}/project-investment-wallet")
    public ResponseEntity<ProjectWalletResponse> getProjectInvestmentWallet(@PathVariable("project_id") int projectId);

    //View ProjectPaymentWallet
    @Operation(
            summary = "PO view payment-investment-wallet",
            description = "PO view payment-investment-wallet of owned project"
    )
    @GetMapping("project/{project_id}/project-payment-wallet")
    public ResponseEntity<ProjectWalletResponse> getProjectPaymentWallet(@PathVariable("project_id") int projectId);


    //Transact money from GeneralWallet to ProjectPaymentWallet

    //Insert period report

    // Make payment to investor due to period


}
