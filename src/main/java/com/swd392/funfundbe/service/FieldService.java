package com.swd392.funfundbe.service;

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
}
