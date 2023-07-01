package com.swd392.funfundbe.controller.api;

import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Request.ProjectStatusRequest;
import com.swd392.funfundbe.model.Response.ProjectResponse;
import com.swd392.funfundbe.model.Response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
@Tag(name = "admin")
public interface AdminController {
        @Operation(summary = "Approve PO registration", description = "Approve PO registration")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Approve PO successfully")
        })
        @PutMapping("/approvePO")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<?> approvePO(@RequestParam("poId") Integer poId)
                        throws CustomNotFoundException, CustomBadRequestException;

        @Operation(summary = "Reject PO registration", description = "Reject PO registration")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Reject PO successfully")
        })
        @PutMapping("/rejectPO")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<?> rejectPO(@RequestParam("poId") Integer poId)
                        throws CustomNotFoundException, CustomBadRequestException;

        @Operation(summary = "Get all users", description = "Get all users")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Get all users successfully")
        })
        @GetMapping("/all-user")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<List<UserResponse>> getAllUsers();

        @Operation(summary = "Approve a project", description = "Approve a project")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Approve project successfully")
        })
        @PutMapping("/approve-project")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<?> approveProject(@RequestParam("project_id") Integer projectId)
                        throws CustomNotFoundException, CustomBadRequestException;

        @Operation(summary = "Reject a project", description = "Reject a project")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Reject project successfully")
        })
        @PutMapping("/reject-project")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<?> rejectProject(@RequestParam("project_id") Integer projectId)
                        throws CustomNotFoundException, CustomBadRequestException;

        @Operation(summary = "filter project by status")
        @GetMapping("/getByStatus")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<List<ProjectResponse>> filterProjectByStatus(
                        @RequestParam(required = false) String status)
                        throws CustomNotFoundException, CustomForbiddenException;
}
