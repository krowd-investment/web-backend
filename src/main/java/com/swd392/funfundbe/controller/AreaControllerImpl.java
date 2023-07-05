package com.swd392.funfundbe.controller;

import com.swd392.funfundbe.controller.api.AreaController;
import com.swd392.funfundbe.model.Response.AreaResponse;
import com.swd392.funfundbe.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AreaControllerImpl implements AreaController {

    private final AreaService areaService;

    @Override
    public ResponseEntity<List<AreaResponse>> getAllAreas() {
        List<AreaResponse> responses = areaService.getAllAreas();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
