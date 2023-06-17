package com.swd392.funfundbe.model.mapper;

import com.swd392.funfundbe.model.Response.ProjectDetailResponse;
import com.swd392.funfundbe.model.Response.ProjectResponse;
import com.swd392.funfundbe.model.entity.Project;

public class ObjectMapper {
    public static ProjectResponse fromProjectToProjectResponse(Project project) {
        return ProjectResponse.builder().projectId(project.getProjectId()).projectName(project.getProjectName())
                .createAt(project.getCreateAt())
                .description(project.getProjectDescription())
                .image(project.getImage()).build();
    }

    public static ProjectDetailResponse fromProjectToProjectDetailResponse(Project project) {
        return ProjectDetailResponse.builder().duration(project.getDuration())
                .mutiplier(project.getMultiplier())
                .image(project.getImage())
                .projectName(project.getProjectName())
                .targetCapital(project.getInvestmentTargetCapital())
                .capital(project.getInvestedCapital())
                .sharedRevenue(project.getSharedRevenue())
                .build();
    }
}
