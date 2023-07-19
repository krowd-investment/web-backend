package com.swd392.funfundbe.model.mapper;

import java.util.Date;

import com.swd392.funfundbe.model.Request.CreateProjectRequest;
import com.swd392.funfundbe.model.Request.InvestProjectRequest;
import com.swd392.funfundbe.model.Response.*;
import com.swd392.funfundbe.model.entity.*;

public class ObjectMapper {
    public static ProjectResponse fromProjectToProjectResponse(Project project) {
        return ProjectResponse.builder().projectId(project.getProjectId()).projectName(project.getProjectName())
                .createAt(project.getCreateAt())
                .description(project.getProjectDescription())
                .brand(project.getBrand())
                .image(project.getImage())
                .endDate(project.getEndDate())
                .startDate(project.getStartDate())
                .status(project.getStatus())
                .build();
    }

    public static ProjectDetailResponse fromProjectToProjectDetailResponse(Project project) {
        return ProjectDetailResponse.builder().duration(project.getDuration())
                .mutiplier(project.getMultiplier())
                .image(project.getImage())
                .projectName(project.getProjectName())
                .targetCapital(project.getInvestmentTargetCapital())
                .capital(project.getInvestedCapital())
                .sharedRevenue(project.getSharedRevenue())
                .brand(project.getBrand())
                .endDate(project.getEndDate())
                .startDate(project.getStartDate())
                .status(project.getStatus())
                .build();
    }

    public static InvestedProjectResponse fromInvestmentToInvestedResponse(Investment investment) {
        return InvestedProjectResponse.builder().projectId(investment.getProject().getProjectId())
                .projectName(investment.getProject().getProjectName())
                .mulplier(investment.getProject().getMultiplier())
                .image(investment.getProject().getImage())
                .totalMoney(investment.getTotalMoney())
                .createAt(investment.getCreatedAt())
                .updateAt(investment.getUpdatedAt())
                .status(investment.getStatus())
                .duration(investment.getProject().getDuration())
                .userId(investment.getInvestmentUser().getUserId())
                .build();
    }

    public static Investment fromInvestmentRequestToInvestment(InvestProjectRequest request) {
        return Investment.builder().createdAt(request.getCreateAt()).totalMoney(request.getTotalMoney())
                .updatedAt(request.getCreateAt())
                .contract("")
                .status(request.getStatus()).build();
    }

    public static FieldResponse fromFieldToFieldResponse(Field field) {
        if (field == null) return null;
        FieldResponse fieldResponse = FieldResponse.builder()
                .fieldId(field.getFieldId())
                .name(field.getName())
                .fieldDescription(field.getFieldDescription())
                .status(field.isStatus())
                .build();
        return fieldResponse;
    }

    public static AreaResponse fromAreaToAreaResponse(Area area) {
        if (area == null) return null;
        AreaResponse areaResponse = AreaResponse.builder()
                .areaId(area.getAreaId())
                .city(area.getCity())
                .district(area.getDistrict())
                .details(area.getDetails())
                .status(area.isStatus())
                .build();
        return areaResponse;
    }

    public static ProjectWalletResponse fromProjectWalletToProjectWalletResponse(ProjectWallet projectWallet) {
        if (projectWallet == null) return null;
        return ProjectWalletResponse.builder()
                .projectWalletId(projectWallet.getProjectWalletId())
                .walletTypeId(projectWallet.getWalletType().getWalletTypeId())
                .projectId(projectWallet.getProject().getProjectId())
                .balance(projectWallet.getBalance())
                .updatedAt(projectWallet.getUpdatedAt())
                .status(projectWallet.isStatus())
                .build();
    }
}
