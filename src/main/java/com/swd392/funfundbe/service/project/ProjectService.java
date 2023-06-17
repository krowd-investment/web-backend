package com.swd392.funfundbe.service.project;

import java.util.List;

import com.swd392.funfundbe.controller.api.exception.custom.CustomUnauthorizedException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Request.AreaFilterRequest;
import com.swd392.funfundbe.model.Request.FieldFilterRequest;
import com.swd392.funfundbe.model.Request.TargetCapitalFilterRequest;
import com.swd392.funfundbe.model.Response.ProjectDetailResponse;
import com.swd392.funfundbe.model.Response.ProjectResponse;

public interface ProjectService {
        public List<ProjectResponse> getAllProject()
                        throws CustomForbiddenException, CustomNotFoundException, CustomUnauthorizedException;

        public ProjectDetailResponse getProjectDetailById(int id)
                        throws CustomForbiddenException, CustomNotFoundException;

        public List<ProjectResponse> filterProjectByAreaName(AreaFilterRequest area) throws CustomForbiddenException, CustomNotFoundException;

        public List<ProjectResponse> filterProjectByFieldName(FieldFilterRequest field) throws CustomForbiddenException, CustomNotFoundException;

        public List<ProjectResponse> filterProjectByTargetCapital(TargetCapitalFilterRequest target) throws CustomForbiddenException, CustomNotFoundException;

}
