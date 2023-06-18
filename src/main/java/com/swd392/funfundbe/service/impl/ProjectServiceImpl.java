package com.swd392.funfundbe.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.swd392.funfundbe.controller.api.exception.custom.CustomUnauthorizedException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.CustomError;
import com.swd392.funfundbe.model.Request.AreaFilterRequest;
import com.swd392.funfundbe.model.Request.FieldFilterRequest;
import com.swd392.funfundbe.model.Request.TargetCapitalFilterRequest;
import com.swd392.funfundbe.model.Response.ProjectDetailResponse;
import com.swd392.funfundbe.model.Response.ProjectResponse;
import com.swd392.funfundbe.model.entity.Project;
import com.swd392.funfundbe.model.enums.ProjectStatus;
import com.swd392.funfundbe.model.mapper.ObjectMapper;
import com.swd392.funfundbe.repository.AreaRepository;
import com.swd392.funfundbe.repository.FieldRepository;
import com.swd392.funfundbe.repository.ProjectRepository;
import com.swd392.funfundbe.service.AuthenticateService;
import com.swd392.funfundbe.service.project.ProjectService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final AreaRepository areaRepository;
    private final FieldRepository fieldRepository;

    @Override
    public List<ProjectResponse> getAllProject()
            throws CustomForbiddenException, CustomNotFoundException, CustomUnauthorizedException {
        boolean check = AuthenticateService.checkCurrentUser();
        if (!check) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can't access this api").field("user_status").build());
        }
        ArrayList<Project> projectList = projectRepository.getAllProjects();
        if (projectList == null) {
            throw new CustomNotFoundException(
                    CustomError.builder().code("404").message("not found any project list").field("project").build());
        }
        List<Project> projectApproved = projectList.stream()
                .filter(p -> p.getStatus().equalsIgnoreCase(ProjectStatus.APPROVED.toString())).toList();
        List<ProjectResponse> projectResponseList = projectApproved.stream().map(p -> {
            String areaCity = areaRepository.findByAreaId(p.getArea().getAreaId()).getCity();
            String areaDistrict = areaRepository.findByAreaId(p.getArea().getAreaId()).getDistrict();
            String areaName = areaCity + "-" + areaDistrict;
            String filedName = fieldRepository.findByFieldId(p.getField().getFieldId()).getName();
            ProjectResponse projectResponse = ObjectMapper
                    .fromProjectToProjectResponse(p);
            projectResponse.setAreaName(areaName);
            projectResponse.setFieldName(filedName);
            return projectResponse;
        }).toList();
        return projectResponseList;
    }

    @Override
    public ProjectDetailResponse getProjectDetailById(int id) throws CustomForbiddenException, CustomNotFoundException {
        boolean check = AuthenticateService.checkCurrentUser();
        if (!check) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can't access this api").field("user_status").build());
        }
        Project project = projectRepository.findByProjectId(id);
        if (project == null) {
            throw new CustomNotFoundException(
                    CustomError.builder().code("404").message("not found any project match id").field("project")
                            .build());
        }
        ProjectDetailResponse detailResponse = ObjectMapper.fromProjectToProjectDetailResponse(project);
        return detailResponse;
    }

    private String buildString(String s1, String s2) {
        return s1.trim() + s2.trim();
    }

    @Override
    public List<ProjectResponse> filterProjectByAreaName(AreaFilterRequest area)
            throws CustomForbiddenException, CustomNotFoundException {
        boolean check = AuthenticateService.checkCurrentUser();
        if (!check) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can't access this api").field("user_status").build());
        }
        List<Project> projects = projectRepository.findAll();
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for (Project p : projects) {
            String areaNameCheck = buildString(p.getArea().getCity(), p.getArea().getDistrict());
            boolean chck = areaNameCheck.toLowerCase().contains(area.getAreaName().trim().toLowerCase());
            if (chck && p.getStatus().equalsIgnoreCase("APPROVED")) {
                ProjectResponse projectResponse = ObjectMapper.fromProjectToProjectResponse(p);
                projectResponse.setFieldName(p.getField().getName());
                projectResponse.setAreaName(p.getArea().getCity() + "-" + p.getArea().getDistrict());
                projectResponses.add(projectResponse);
            }
        }
        return projectResponses;

    }

    @Override
    public List<ProjectResponse> filterProjectByFieldName(FieldFilterRequest field)
            throws CustomForbiddenException, CustomNotFoundException {
        boolean check = AuthenticateService.checkCurrentUser();
        if (!check) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can't access this api").field("user_status").build());
        }
        List<Project> projects = projectRepository.findAll();
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for (Project p : projects) {
            String fieldCheck = p.getField().getName();
            boolean chck = fieldCheck.toLowerCase().contains(field.getFieldName().trim().toLowerCase());
            if (chck && p.getStatus().equalsIgnoreCase("APPROVED")) {
                ProjectResponse projectResponse = ObjectMapper.fromProjectToProjectResponse(p);
                projectResponse.setFieldName(p.getField().getName());
                projectResponse.setAreaName(p.getArea().getCity() + "-" + p.getArea().getDistrict());
                projectResponses.add(projectResponse);
            }
        }
        return projectResponses;

    }

    @Override
    public List<ProjectResponse> filterProjectByTargetCapital(TargetCapitalFilterRequest target)
            throws CustomForbiddenException, CustomNotFoundException {
        boolean check = AuthenticateService.checkCurrentUser();
        if (!check) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can't access this api").field("user_status").build());
        }
        List<Project> projects = projectRepository.findAll();
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for (Project p : projects) {
            BigDecimal targetCheck = p.getInvestmentTargetCapital();
            boolean chck = targetCheck.doubleValue() == target.getTarget().doubleValue();
            if (chck && p.getStatus().equalsIgnoreCase("APPROVED")) {
                ProjectResponse projectResponse = ObjectMapper.fromProjectToProjectResponse(p);
                projectResponse.setFieldName(p.getField().getName());
                projectResponse.setAreaName(p.getArea().getCity() + "-" + p.getArea().getDistrict());
                projectResponses.add(projectResponse);
            }
        }
        return projectResponses;

    }
}
