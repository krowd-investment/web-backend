package com.swd392.funfundbe.controller.api;

import java.math.BigDecimal;
import java.util.List;

import com.swd392.funfundbe.model.Response.ProjectWalletResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.swd392.funfundbe.controller.api.exception.custom.CustomUnauthorizedException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Request.AreaFilterRequest;
import com.swd392.funfundbe.model.Request.FieldFilterRequest;
import com.swd392.funfundbe.model.Request.ProjectStatusRequest;
import com.swd392.funfundbe.model.Request.TargetCapitalFilterRequest;
import com.swd392.funfundbe.model.Response.ProjectDetailResponse;
import com.swd392.funfundbe.model.Response.ProjectResponse;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/api/projects")
@Tag(name = "project")
public interface ProjectController {
        @Operation(summary = "get all project has status is approved")
        @GetMapping("/getAll")
        public ResponseEntity<List<ProjectResponse>> getAllProject()
                        throws CustomForbiddenException, CustomNotFoundException, CustomUnauthorizedException;

        @Operation(summary = "get project by id")
        @GetMapping("/project/{id}")
        public ResponseEntity<ProjectDetailResponse> getProjectById(@PathVariable("id") int id)
                        throws CustomForbiddenException, CustomNotFoundException;

        @Operation(summary = "filter project by area")
        @GetMapping("/filterByAreaName")
        public ResponseEntity<List<ProjectResponse>> filterProject(
                        @RequestParam String area) throws CustomForbiddenException, CustomNotFoundException;

        @Operation(summary = "filter project by filed")
        @GetMapping("/filterByField")
        public ResponseEntity<List<ProjectResponse>> filterProjectByField(
                        @RequestParam String field)
                        throws CustomForbiddenException, CustomNotFoundException;

        @Operation(summary = "filter project by target")
        @GetMapping("/filterByTarget")
        public ResponseEntity<List<ProjectResponse>> filterProjectByTarget(
                        @RequestParam BigDecimal target)
                        throws CustomForbiddenException, CustomNotFoundException;

        // View ProjectInvestmentWallet
        @Operation(summary = "View project-investment-wallet", description = "View project-investment-wallet of specific project")
        @GetMapping("/{project_id}/project-investment-wallet")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'PO')")
        public ResponseEntity<ProjectWalletResponse> getProjectInvestmentWallet(
                @PathVariable("project_id") int projectId) throws CustomNotFoundException, CustomForbiddenException;

        // View ProjectPaymentWallet
        @Operation(summary = "View payment-investment-wallet", description = "View payment-investment-wallet of specific project")
        @GetMapping("/{project_id}/project-payment-wallet")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'PO')")
        public ResponseEntity<ProjectWalletResponse> getProjectPaymentWallet(
                @PathVariable("project_id") int projectId) throws CustomNotFoundException, CustomForbiddenException;



}
