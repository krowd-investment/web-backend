package com.swd392.funfundbe.controller.api;

import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Response.FieldResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/fields")
@Tag(name = "field")
public interface FieldController {


    @Operation(summary = "Get all fields of projects", description = "Get all fields of projects")
    @GetMapping
    public ResponseEntity<List<FieldResponse>> getAllFields();

    @Operation(summary = "Get all field of project by ID", description = "Get all field of project by ID")
    @GetMapping("/{field_id}")
    public ResponseEntity<FieldResponse> getFieldById(@PathVariable("field_id") int fieldId) throws CustomNotFoundException;

}
