package com.swd392.funfundbe.service.project;

import java.math.BigDecimal;
import java.util.List;

import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomUnauthorizedException;
import com.swd392.funfundbe.model.Request.CreateProjectRequest;
import com.swd392.funfundbe.model.Request.UpdateProjectRequest;
import com.swd392.funfundbe.model.Response.ProjectDetailResponse;
import com.swd392.funfundbe.model.Response.ProjectResponse;

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

        public String createProject(CreateProjectRequest projectRequest)
                        throws CustomNotFoundException, CustomForbiddenException;

        public List<ProjectResponse> getAllProjectByStatus(String status)
                        throws CustomNotFoundException, CustomForbiddenException;

        public List<ProjectResponse> getProjectOfCurrentUser() throws CustomNotFoundException;

        public String updateProject(int id, UpdateProjectRequest request)
                        throws CustomNotFoundException, CustomForbiddenException;
}
