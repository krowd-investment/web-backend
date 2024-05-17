package com.swd392.funfundbe.controller;

import com.swd392.funfundbe.controller.api.FieldController;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Response.FieldResponse;
import com.swd392.funfundbe.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FieldControllerImpl implements FieldController {

    private final FieldService fieldService;
    @Override
    public ResponseEntity<List<FieldResponse>> getAllFields() {
        List<FieldResponse> response = fieldService.getAllFields();
        return new ResponseEntity<>(
                response, HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<FieldResponse> getFieldById(int fieldId) throws CustomNotFoundException {
        FieldResponse fieldResponse = fieldService.getFieldById(fieldId);
        return ResponseEntity.ok(fieldResponse);
    }
}
