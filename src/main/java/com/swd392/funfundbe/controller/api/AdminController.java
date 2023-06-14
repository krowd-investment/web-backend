package com.swd392.funfundbe.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin")
public interface AdminController {
    @Operation(
            summary = "Approve PO registration",
            description = "Approve PO registration",
            tags = "admin"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Approve PO successfully")
    })
    @PutMapping("/approvePO")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> approvePO(@RequestParam("poId") Integer poId);

    @Operation(
            summary = "Reject PO registration",
            description = "Reject PO registration",
            tags = "admin"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reject PO successfully")
    })
    @PutMapping("/rejectPO")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> rejectPO(@RequestParam("poId") Integer poId);
}
