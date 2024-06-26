package com.swd392.funfundbe.controller;

import com.swd392.funfundbe.model.Response.ProjectWalletResponse;
import com.swd392.funfundbe.model.Response.StageResponse;
import com.swd392.funfundbe.model.enums.WalletTypeString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.swd392.funfundbe.controller.api.ProjectController;
import com.swd392.funfundbe.controller.api.exception.custom.CustomUnauthorizedException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Request.AreaFilterRequest;
import com.swd392.funfundbe.model.Request.FieldFilterRequest;
import com.swd392.funfundbe.model.Request.ProjectStatusRequest;
import com.swd392.funfundbe.model.Request.TargetCapitalFilterRequest;
import com.swd392.funfundbe.model.Request.UpdateProjectRequest;
import com.swd392.funfundbe.model.Response.ProjectDetailResponse;
import com.swd392.funfundbe.model.Response.ProjectResponse;
import com.swd392.funfundbe.service.project.ProjectService;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.*;;

@RestController
@RequiredArgsConstructor
public class ProjectControllerImpl implements ProjectController {
    private final ProjectService projectService;

    @Override
    public ResponseEntity<List<ProjectResponse>> getAllProject()
            throws CustomForbiddenException, CustomNotFoundException, CustomUnauthorizedException {
        List<ProjectResponse> projectResponses = projectService.getAllProject();
        return ResponseEntity.ok(projectResponses);
    }

    @Override
    public ResponseEntity<ProjectDetailResponse> getProjectById(int id)
            throws CustomForbiddenException, CustomNotFoundException {
        ProjectDetailResponse project = projectService.getProjectDetailById(id);
        return ResponseEntity.ok(project);
    }

    @Override
    public ResponseEntity<List<ProjectResponse>> filterProject(String area)
            throws CustomForbiddenException, CustomNotFoundException {
        List<ProjectResponse> projectResponses = projectService.filterProjectByAreaName(area);
        return ResponseEntity.ok(projectResponses);
    }

    @Override
    public ResponseEntity<List<ProjectResponse>> filterProjectByField(String filterRequest)
            throws CustomForbiddenException, CustomNotFoundException {
        List<ProjectResponse> projectResponses = projectService.filterProjectByFieldName(filterRequest);
        return ResponseEntity.ok(projectResponses);
    }

    @Override
    public ResponseEntity<List<ProjectResponse>> filterProjectByTarget(BigDecimal target)
            throws CustomForbiddenException, CustomNotFoundException {
        List<ProjectResponse> projectResponses = projectService.filterProjectByTargetCapital(target);
        return ResponseEntity.ok(projectResponses);
    }

    @Override
    public ResponseEntity<ProjectWalletResponse> getProjectInvestmentWallet(int projectId) throws CustomNotFoundException, CustomForbiddenException {
        ProjectWalletResponse projectWalletResponse = projectService.getProjectWalletResponse(
                projectId,
                WalletTypeString.PROJECT_INVESTMENT_WALLET
        );
        return new ResponseEntity<>(projectWalletResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProjectWalletResponse> getProjectPaymentWallet(int projectId) throws CustomNotFoundException, CustomForbiddenException {
        ProjectWalletResponse projectWalletResponse = projectService.getProjectWalletResponse(projectId, WalletTypeString.PROJECT_PAYMENT_WALLET);
        return new ResponseEntity<>(projectWalletResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<StageResponse>> getAllStagesOfProject(int projectId) throws CustomNotFoundException, CustomForbiddenException {
        List<StageResponse> stages = projectService.getAllStagesOfProject(projectId);
        return ResponseEntity.ok(stages);
    }

}
