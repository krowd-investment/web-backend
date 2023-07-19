package com.swd392.funfundbe.service.project;

import java.math.BigDecimal;
import java.util.List;

import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomUnauthorizedException;
import com.swd392.funfundbe.model.Request.CreateProjectRequest;
import com.swd392.funfundbe.model.Request.UpdateProjectRequest;
import com.swd392.funfundbe.model.Response.ProjectDetailResponse;
import com.swd392.funfundbe.model.Response.ProjectResponse;
import com.swd392.funfundbe.model.Response.ProjectWalletResponse;
import com.swd392.funfundbe.model.enums.WalletTypeString;

public interface ProjectService {
        public List<ProjectResponse> getAllProject()
                        throws CustomForbiddenException, CustomNotFoundException, CustomUnauthorizedException;

        public ProjectDetailResponse getProjectDetailById(int id)
                        throws CustomForbiddenException, CustomNotFoundException;

        public List<ProjectResponse> filterProjectByAreaName(String area)
                        throws CustomForbiddenException, CustomNotFoundException;

        public List<ProjectResponse> filterProjectByFieldName(String field)
                        throws CustomForbiddenException, CustomNotFoundException;

        public List<ProjectResponse> filterProjectByTargetCapital(BigDecimal target)
                        throws CustomForbiddenException, CustomNotFoundException;

        public ProjectResponse createProject(CreateProjectRequest projectRequest)
                        throws CustomNotFoundException, CustomForbiddenException;

        public List<ProjectResponse> getAllProjectByStatus(String status)
                        throws CustomNotFoundException, CustomForbiddenException;

        public List<ProjectResponse> getProjectOfCurrentUser() throws CustomNotFoundException;

        public ProjectWalletResponse getProjectWalletResponse(int projectId, WalletTypeString walletTypeString)
                throws CustomNotFoundException, CustomForbiddenException;
        public ProjectResponse updateProject(int id, UpdateProjectRequest request) throws CustomNotFoundException, CustomForbiddenException, CustomBadRequestException;
}
