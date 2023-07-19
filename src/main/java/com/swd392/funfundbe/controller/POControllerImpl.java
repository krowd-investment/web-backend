package com.swd392.funfundbe.controller;

import com.swd392.funfundbe.controller.api.POController;
import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Request.CreateProjectRequest;
import com.swd392.funfundbe.model.Request.UpdateProjectRequest;
import com.swd392.funfundbe.model.Response.ProjectResponse;
import com.swd392.funfundbe.model.Response.ProjectWalletResponse;
import com.swd392.funfundbe.model.enums.WalletTypeString;
import com.swd392.funfundbe.service.project.ProjectService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class POControllerImpl implements POController {
    private final ProjectService projectService;

    @Override
    public ResponseEntity<?> createProject(CreateProjectRequest request)
            throws CustomNotFoundException, CustomForbiddenException {
        ProjectResponse result = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<?> updateProject(int id, UpdateProjectRequest request) throws CustomNotFoundException, CustomForbiddenException, CustomBadRequestException {
        ProjectResponse result = projectService.updateProject(id, request);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<?> deleteProject(int projectId) {
        return new ResponseEntity<>("Delete project successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> startProject(int projectId) {
        return new ResponseEntity<>("Start project successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ProjectResponse>> getAllProjectOfCurrentPO() throws CustomNotFoundException {
        List<ProjectResponse> projectResponses = projectService.getProjectOfCurrentUser();
        return ResponseEntity.ok(projectResponses);
    }
}
