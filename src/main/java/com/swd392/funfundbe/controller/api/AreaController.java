package com.swd392.funfundbe.controller.api;

import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Response.AreaResponse;
import com.swd392.funfundbe.model.Response.FieldResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/areas")
@Tag(name = "area")
public interface AreaController {

    @Operation(summary = "Get all areas of projects", description = "Get all areas of projects")
    @GetMapping
    public ResponseEntity<List<AreaResponse>> getAllAreas();

    @Operation(summary = "Get area of project by ID", description = "Get are of project by ID")
    @GetMapping("/{area_id}")
    public ResponseEntity<AreaResponse> getAreaById(@PathVariable("area_id") int area_id) throws CustomNotFoundException;
}
