package com.swd392.funfundbe.controller.api;

import java.util.List;

import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.model.Request.UpdateProjectRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Request.CreateProjectRequest;
import com.swd392.funfundbe.model.Response.ProjectResponse;
import com.swd392.funfundbe.model.Response.ProjectWalletResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/api/po")
@Tag(name = "project owner")
public interface POController {

        // Create projects and submit it to admin
        @Operation(summary = "PO create project", description = "PO create project for investment")
        @PostMapping("/project")
        public ResponseEntity<?> createProject(@RequestBody @Valid CreateProjectRequest request)
                        throws CustomNotFoundException, CustomForbiddenException;

        // Update project (only before receiving money)
        @Operation(summary = "PO update project", description = "PO update project for investment")
        @PutMapping("/project/{project_id}")
        public ResponseEntity<?> updateProject(@PathVariable("project_id") int id, @RequestBody UpdateProjectRequest request) throws CustomNotFoundException, CustomForbiddenException, CustomBadRequestException;

        // Delete project (only before receiving money)
        @Operation(summary = "PO delete project", description = "PO delete project, oly available before receiving money")
        @DeleteMapping("/project/{project_id}")
        public ResponseEntity<?> deleteProject(@PathVariable("project_id") int projectId);

        // Start projects
        @Operation(summary = "PO start project", description = "PO start project after receiving enough capital")
        @PutMapping("/project/start/{project_id}")
        public ResponseEntity<?> startProject(@PathVariable("project_id") int projectId);

        // Transact money from GeneralWallet to ProjectPaymentWallet

        // Insert period report

        // Make payment to investor due to period
        @PreAuthorize("hasAuthority('PO')")
        @Operation(summary = "get all project of PO")
        @GetMapping("/project/getProject")
        public ResponseEntity<List<ProjectResponse>> getAllProjectOfCurrentPO() throws CustomNotFoundException;

}
