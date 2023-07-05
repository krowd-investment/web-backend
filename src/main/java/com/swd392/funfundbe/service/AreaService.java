package com.swd392.funfundbe.service;

import com.swd392.funfundbe.model.Response.AreaResponse;
import com.swd392.funfundbe.model.entity.Area;
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
}
