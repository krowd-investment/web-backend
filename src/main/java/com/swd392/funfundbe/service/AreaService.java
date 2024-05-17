package com.swd392.funfundbe.service;

import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.CustomError;
import com.swd392.funfundbe.model.Response.AreaResponse;
import com.swd392.funfundbe.model.Response.FieldResponse;
import com.swd392.funfundbe.model.entity.Area;
import com.swd392.funfundbe.model.entity.Field;
import com.swd392.funfundbe.model.mapper.ObjectMapper;
import com.swd392.funfundbe.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaService {

    private final AreaRepository areaRepository;

    public List<AreaResponse> getAllAreas() {
        List<Area> areas = areaRepository.findAll();
        List<AreaResponse> areaResponses = areas.stream()
                .map(ObjectMapper::fromAreaToAreaResponse)
                .toList();
        return areaResponses;
    }

    public AreaResponse getAreaById(int areaId) throws CustomNotFoundException {
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new CustomNotFoundException(CustomError.builder().message("Area ID not found").build()));
        return ObjectMapper.fromAreaToAreaResponse(area);
    }
}
