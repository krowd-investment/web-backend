package com.swd392.funfundbe.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.model.Response.ProjectWalletResponse;
import com.swd392.funfundbe.model.Response.StageResponse;
import com.swd392.funfundbe.model.entity.*;
import com.swd392.funfundbe.model.enums.StageStatus;
import com.swd392.funfundbe.model.enums.WalletTypeString;
import com.swd392.funfundbe.repository.*;
import org.springframework.stereotype.Service;

import com.swd392.funfundbe.controller.api.exception.custom.CustomUnauthorizedException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.CustomError;
import com.swd392.funfundbe.model.Request.AreaFilterRequest;
import com.swd392.funfundbe.model.Request.CreateProjectRequest;
import com.swd392.funfundbe.model.Request.FieldFilterRequest;
import com.swd392.funfundbe.model.Request.TargetCapitalFilterRequest;
import com.swd392.funfundbe.model.Request.UpdateProjectRequest;
import com.swd392.funfundbe.model.Response.ProjectDetailResponse;
import com.swd392.funfundbe.model.Response.ProjectResponse;
import com.swd392.funfundbe.model.enums.ProjectStatus;
import com.swd392.funfundbe.model.enums.Role;
import com.swd392.funfundbe.model.mapper.ObjectMapper;
import com.swd392.funfundbe.service.AuthenticateService;
import com.swd392.funfundbe.service.project.ProjectService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final AreaRepository areaRepository;
    private final FieldRepository fieldRepository;
    private final ProjectWalletRepository projectWalletRepository;
    private final StageRepository stageRepository;

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
                .filter(p -> p.getStatus().equalsIgnoreCase(ProjectStatus.APPROVED.toString())
                        || p.getStatus().equalsIgnoreCase(ProjectStatus.READY.toString())
                        || p.getStatus().equalsIgnoreCase(ProjectStatus.DONE.toString()))
                .toList();
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
    public List<ProjectResponse> filterProjectByAreaName(String area)
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
            boolean chck = areaNameCheck.toLowerCase().contains(area.trim().toLowerCase());
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
    public List<ProjectResponse> filterProjectByFieldName(String field)
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
            boolean chck = fieldCheck.toLowerCase().contains(field.trim().toLowerCase());
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
    public List<ProjectResponse> filterProjectByTargetCapital(BigDecimal target)
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
            boolean chck = targetCheck.doubleValue() == target.doubleValue();
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
    public ProjectResponse createProject(CreateProjectRequest projectRequest)
            throws CustomNotFoundException, CustomForbiddenException {
        UserTbl user = AuthenticateService.getCurrentUserFromSecurityContext();
        if (!user.getRole().getRoleId().equals(Role.PO.toString()) || !AuthenticateService.checkCurrentUser()) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("you can't access this feature").build());
        }
        Area area = areaRepository.findByAreaId(projectRequest.getAreaId());
        Field field = fieldRepository.findByFieldId(projectRequest.getFieldId());
        if (area == null) {
            throw new CustomNotFoundException(
                    CustomError.builder().code("404").message("not found area matched").build());
        }
        if (field == null) {
            throw new CustomNotFoundException(
                    CustomError.builder().code("404").message("not found field matched").build());
        }
        Project project = Project.builder().field(field).area(area).projectName(projectRequest.getProjectName())
                .projectCreatedBy(user)
                .brand(projectRequest.getBrand())
                .investedCapital(new BigDecimal(0))
                .investmentTargetCapital(projectRequest.getInvestmentTargetCapital())
                .sharedRevenue(projectRequest.getSharedRevenue())
                .multiplier(projectRequest.getMultiplier())
                .numberOfStage(projectRequest.getNumberOfStage())
                .duration(projectRequest.getDuration())
                .startDate(projectRequest.getStartDate())
                .endDate(projectRequest.getEndDate())
                .image(projectRequest.getImage())
                .projectDescription(projectRequest.getProjectDescription())
                .businessLicense(projectRequest.getBusinessLicense())
                .paidAmount(new BigDecimal(0))
                .remainingAmount(new BigDecimal(0))
                .investedCapital(new BigDecimal(0))
                .status(ProjectStatus.PENDING.toString())
                .createAt(new Date())
                .projectCreatedBy(user)
                .build();
        Project save = projectRepository.save(project);
        return ObjectMapper.fromProjectToProjectResponse(save);
    }

    @Override
    public List<ProjectResponse> getAllProjectByStatus(String status)
            throws CustomNotFoundException, CustomForbiddenException {
        if (!AuthenticateService.getCurrentUserFromSecurityContext().getRole().getRoleName()
                .equalsIgnoreCase("ADMIN")) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("You can't access this feature").build());
        }
        if (!AuthenticateService.checkCurrentUser()) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("You can't access this feature").build());
        }
        if (status == null) {
            List<Project> projects = projectRepository.findAll();
            List<ProjectResponse> projectResponseList = projects.stream().map(p -> {
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
        List<Project> projectList = projectRepository.findAll().stream()
                .filter(x -> x.getStatus().equalsIgnoreCase(status)).toList();
        List<ProjectResponse> projectResponses = projectList.stream().map(p -> {
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
        return projectResponses;
    }

    @Override
    public List<ProjectResponse> getProjectOfCurrentUser() throws CustomNotFoundException {
        UserTbl user = AuthenticateService.getCurrentUserFromSecurityContext();
        List<Project> projects = projectRepository.findAll().stream()
                .filter(x -> x.getProjectCreatedBy().getUserId() == user.getUserId()).toList();
        List<ProjectResponse> projectResponses = projects.stream().map(p -> {
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
        return projectResponses;
    }

    @Override
    public ProjectWalletResponse getProjectWalletResponse(int projectId, WalletTypeString walletTypeString) throws CustomNotFoundException, CustomForbiddenException {
        UserTbl user = AuthenticateService.getCurrentUserFromSecurityContext();
        if (!user.isEnabled()) {
            throw new CustomForbiddenException(
                    CustomError.builder().message("Can't access this api").build()
            );
        }
        if (user.getRole().getRoleId().equals(Role.ADMIN.toString())) {
            return ObjectMapper.fromProjectWalletToProjectWalletResponse(getProjectWallet(projectId, walletTypeString));
        }
        else if (user.getRole().getRoleId().equals(Role.PO.toString())) {
            ProjectWallet projectWallet = getProjectWallet(projectId, walletTypeString);
            if (projectWallet.getProject().getProjectCreatedBy().getUserId() != user.getUserId())
                throw new CustomForbiddenException(CustomError.builder().message("Can't view project wallet of other owner's project").build());
            return ObjectMapper.fromProjectWalletToProjectWalletResponse(projectWallet);
        }
        return  null;
    }

    private ProjectWallet getProjectWallet(int projectId, WalletTypeString walletTypeString) throws CustomNotFoundException {
        ProjectWallet projectWallet = projectWalletRepository.findByProject_ProjectIdAndWalletType_WalletTypeId(projectId, walletTypeString.toString())
                .orElseThrow(() -> new CustomNotFoundException(
                        CustomError.builder().message("Project wallet not found with such project ID").build()));
        return projectWallet;
    }
    public ProjectResponse updateProject(int id, UpdateProjectRequest request) throws CustomNotFoundException, CustomForbiddenException, CustomBadRequestException {
        if (!AuthenticateService.getCurrentUserFromSecurityContext().getRole().getRoleId()
                .equals(Role.PO.toString())) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can not access this feature").build());
        }
        Project project = projectRepository.findByProjectId(id);
        if (project == null) {
            throw new CustomNotFoundException(
                    CustomError.builder().code("404").message("not found project matched id").build());
        }

        checkProjectStatusForUpdating(project);

        Area area = areaRepository.findByAreaId(request.getAreaId());
        if (area != null) {
            project.setArea(area);
        }
        Field field = fieldRepository.findByFieldId(request.getFieldId());
        if (field != null) {
            project.setField(field);
        }
        UserTbl user = AuthenticateService.getCurrentUserFromSecurityContext();
        project.setBusinessLicense(request.getBusinessLicense());
        project.setBrand(request.getBrand());
        project.setDuration(request.getDuration());
        project.setImage(request.getImage());
        project.setInvestmentTargetCapital(request.getInvestmentTargetCapital());
        project.setMultiplier(project.getMultiplier());
        project.setNumberOfStage(request.getNumberOfStage());
        project.setProjectDescription(request.getProjectDescription());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setProjectName(request.getProjectName());
        project.setProjectUpdatedBy(user);
        project.setSharedRevenue(request.getSharedRevenue());
        projectRepository.save(project);
        return ObjectMapper.fromProjectToProjectResponse(project);
    }

    private void checkProjectStatusForUpdating(Project project) throws CustomBadRequestException {
        if (!project.getStatus().equals(ProjectStatus.REJECTED.toString()) && !project.getStatus().equals(ProjectStatus.PENDING.toString()))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400").message("Project must be PENDING or REJECTED for updating")
                            .build()
            );
    }

    @Override
    public ProjectDetailResponse startProject(int projectId) throws CustomNotFoundException, CustomForbiddenException, CustomBadRequestException {
        UserTbl user = AuthenticateService.getCurrentUserFromSecurityContext();
        if (!user.isEnabled()) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can not access this feature").build());
        }
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomNotFoundException(
                        CustomError.builder().message("Project ID not found").build()
                ));
        if (project.getProjectCreatedBy().getUserId() != user.getUserId())
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("Can not start project of other owner").build());
        checkProjectStatusForStarting(project);

        project.setRemainingAmount(project.getInvestedCapital().multiply(project.getMultiplier()));
        project.setStatus(ProjectStatus.STARTED.toString());

        Date startDate = new Date();
        for (int i = 1; i <= project.getNumberOfStage(); i++) {
            if (i == 1) startDate = new Date();
            Stage stage = Stage.builder()
                    .name("Stage " + i)
                    .project(project)
                    .startDate(startDate)
                    .endDate(calculateDateByAddingMonths(startDate, project.getDuration()))
                    .isOverDue(false)
                    .stageStatus(i == 1 ? StageStatus.ACTIVE.toString() : StageStatus.INACTIVE.toString())
                    .status(true)
                    .build();
            stageRepository.save(stage);
            startDate = stage.getEndDate();
        }

        return ObjectMapper.fromProjectToProjectDetailResponse(project);
    }

    private void checkProjectStatusForStarting(Project project) throws CustomBadRequestException {
        if (!project.getStatus().equals(ProjectStatus.READY.toString()))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400").message("Project must be READY for starting")
                            .build()
            );
    }

    private Date calculateDateByAddingMonths(Date date, int durationInMonth) {
        int newMonth = (date.getMonth() + durationInMonth) % 12;
        int newYear  = date.getYear() + (date.getMonth() + durationInMonth) / 12;
        return new Date(newYear, newMonth, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds());
    }

    @Override
    public List<StageResponse> getAllStagesOfProject(int projectId) throws CustomNotFoundException, CustomForbiddenException {
        if (!AuthenticateService.checkCurrentUser()) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can not access this feature").build());
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomNotFoundException(
                        CustomError.builder().message("Project ID not found").build()
                ));
        return project.getStageList().stream().map(ObjectMapper::fromStageToStageResponse).toList();
    }
}
