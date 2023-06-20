package com.swd392.funfundbe.service.investment;

import java.util.List;

import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.Request.InvestProjectRequest;
import com.swd392.funfundbe.model.Response.InvestedProjectResponse;

public interface InvestmentService {
    List<InvestedProjectResponse> getAllInvestmentOfCurrentUser() throws CustomNotFoundException;

    InvestedProjectResponse investProject(InvestProjectRequest request)
            throws CustomNotFoundException, CustomForbiddenException;
}
