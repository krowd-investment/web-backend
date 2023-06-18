package com.swd392.funfundbe.controller.api;

import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin")
@Tag(name = "admin")
public interface AdminController {
    @Operation(
            summary = "Approve PO registration",
            description = "Approve PO registration"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Approve PO successfully")
    })
    @PutMapping("/approvePO")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> approvePO(@RequestParam("poId") Integer poId) throws CustomNotFoundException, CustomBadRequestException;

    @Operation(
            summary = "Reject PO registration",
            description = "Reject PO registration"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reject PO successfully")
    })
    @PutMapping("/rejectPO")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> rejectPO(@RequestParam("poId") Integer poId) throws CustomNotFoundException, CustomBadRequestException;
}
