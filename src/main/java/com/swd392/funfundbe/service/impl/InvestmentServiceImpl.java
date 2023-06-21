package com.swd392.funfundbe.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.CustomError;
import com.swd392.funfundbe.model.Request.InvestProjectRequest;
import com.swd392.funfundbe.model.Response.InvestedProjectResponse;
import com.swd392.funfundbe.model.entity.Investment;
import com.swd392.funfundbe.model.entity.PersonalWallet;
import com.swd392.funfundbe.model.entity.Project;
import com.swd392.funfundbe.model.entity.ProjectWallet;
import com.swd392.funfundbe.model.entity.UserTbl;
import com.swd392.funfundbe.model.enums.ProjectStatus;
import com.swd392.funfundbe.model.mapper.ObjectMapper;
import com.swd392.funfundbe.repository.InvestmentRepository;
import com.swd392.funfundbe.repository.PersonalWalletRepository;
import com.swd392.funfundbe.repository.ProjectRepository;
import com.swd392.funfundbe.repository.ProjectWalletRepository;
import com.swd392.funfundbe.repository.UserRepository;
import com.swd392.funfundbe.service.AuthenticateService;
import com.swd392.funfundbe.service.investment.InvestmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvestmentServiceImpl implements InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public List<InvestedProjectResponse> getAllInvestmentOfCurrentUser() throws CustomNotFoundException {
        int userId = AuthenticateService.getCurrentUserFromSecurityContext().getUserId();
        List<Investment> investments = investmentRepository.findAll().stream()
                .filter(i -> i.getInvestmentUser().getUserId() == userId).toList();
        if (investments == null) {
            throw new CustomNotFoundException(
                    CustomError.builder().code("404").message("Not found anything").build());
        }
        List<InvestedProjectResponse> responses = new ArrayList<>();
        for (Investment investment : investments) {
            responses.add(ObjectMapper.fromInvestmentToInvestedResponse(investment));
        }
        return responses;
    }

    @Override
    public InvestedProjectResponse investProject(InvestProjectRequest request)
            throws CustomNotFoundException, CustomForbiddenException {
        boolean check = AuthenticateService.checkCurrentUser();
        if (!check) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can't access this api").field("user_status").build());
        }
        UUID uuid = UUID.randomUUID();
        Project project = projectRepository.findByProjectId(request.getProjectId());
        UserTbl user = AuthenticateService.getCurrentUserFromSecurityContext();
        if (project == null || user == null) {
            throw new CustomNotFoundException(
                    CustomError.builder().code("404").message("Not found information matched").build());
        }
        Investment investment = ObjectMapper.fromInvestmentRequestToInvestment(request);
        if (investment.getStatus().trim().equals("")) {
            investment.setStatus("SUCCESS");
        }
        investment.setProject(project);
        investment.setInvestmentUser(user);
        investment.setInvestmentId(uuid);
        investmentRepository.save(investment);
        return ObjectMapper.fromInvestmentToInvestedResponse(investment);
    }

    @Override
    public InvestedProjectResponse cancelInvested(UUID uuid) throws CustomForbiddenException, CustomNotFoundException {
        boolean check = AuthenticateService.checkCurrentUser();
        if (!check) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can't access this api").field("user_status").build());
        }
        Investment investment = investmentRepository.findById(uuid).orElse(null);
        if (investment == null) {
            throw new CustomNotFoundException(
                    CustomError.builder().code("404").message("Not found anything").build());
        }
        Project project = projectRepository.findByProjectId(investment.getProject().getProjectId());
        if (project.getStatus().equalsIgnoreCase(ProjectStatus.STARTED.toString())) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can't cancel when project started").build());
        }
        investment.setStatus("CANCEL");
        investmentRepository.save(investment);
        InvestedProjectResponse investedProjectResponse = ObjectMapper.fromInvestmentToInvestedResponse(investment);
        return investedProjectResponse;
    }

}
