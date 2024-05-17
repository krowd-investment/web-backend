package com.swd392.funfundbe.service;

import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.CustomError;
import com.swd392.funfundbe.model.Response.FieldResponse;
import com.swd392.funfundbe.model.entity.Field;
import com.swd392.funfundbe.model.mapper.ObjectMapper;
import com.swd392.funfundbe.repository.FieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final FieldRepository fieldRepository;
    public List<FieldResponse> getAllFields() {
        List<Field> fields = fieldRepository.findAll();
        List<FieldResponse> fieldResponses = fields.stream()
                .map(ObjectMapper::fromFieldToFieldResponse)
                .toList();
        return fieldResponses;
    }

    public FieldResponse getFieldById(int fieldId) throws CustomNotFoundException {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new CustomNotFoundException(CustomError.builder().message("Field ID not found").build()));
        return ObjectMapper.fromFieldToFieldResponse(field);
    }
}
