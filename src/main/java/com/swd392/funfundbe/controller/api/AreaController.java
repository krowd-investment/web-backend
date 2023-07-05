package com.swd392.funfundbe.controller.api;

import com.swd392.funfundbe.model.Response.AreaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/areas")
@Tag(name = "area")
public interface AreaController {

    @Operation(summary = "Get all areas of projects", description = "Get all areas of projects")
    @GetMapping
    public ResponseEntity<List<AreaResponse>> getAllAreas();
}
