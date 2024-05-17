package com.swd392.funfundbe.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.swd392.funfundbe.model.enums.InvestmentStatus;
import com.swd392.funfundbe.model.enums.WalletTypeString;
import com.swd392.funfundbe.service.UserService;
import com.swd392.funfundbe.service.project.ProjectService;
import org.springframework.stereotype.Service;

import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.CustomError;
import com.swd392.funfundbe.model.Request.InvestProjectRequest;
import com.swd392.funfundbe.model.Response.InvestedProjectResponse;
import com.swd392.funfundbe.model.entity.Investment;
import com.swd392.funfundbe.model.entity.InvestmentTransaction;
import com.swd392.funfundbe.model.entity.PersonalWallet;
import com.swd392.funfundbe.model.entity.Project;
import com.swd392.funfundbe.model.entity.ProjectWallet;
import com.swd392.funfundbe.model.entity.UserTbl;
import com.swd392.funfundbe.model.enums.InvestmentTransactionType;
import com.swd392.funfundbe.model.enums.ProjectStatus;
import com.swd392.funfundbe.model.mapper.ObjectMapper;
import com.swd392.funfundbe.repository.InvestmentRepository;
import com.swd392.funfundbe.repository.InvestmentTransactionRepo;
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
    private final PersonalWalletRepository personalWalletRepository;
    private final ProjectWalletRepository projectWalletRepository;
    private final InvestmentTransactionRepo investmentTransactionRepository;
    private final UserService userService;
    private final ProjectService projectService;

    @Override
    public List<InvestedProjectResponse> getAllInvestmentOfCurrentUser() throws CustomNotFoundException, CustomForbiddenException {
        UserTbl user = AuthenticateService.getCurrentUserFromSecurityContext();
        if (!user.isEnabled())
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can't access this api").field("user_status").build());
        List<Investment> investments = investmentRepository.findAll().stream()
                .filter(i -> i.getInvestmentUser().getUserId() == user.getUserId()).toList();
        return investments.stream().map(ObjectMapper::fromInvestmentToInvestedResponse).toList();
    }

    @Override
    public InvestedProjectResponse investProject(InvestProjectRequest request)
            throws CustomNotFoundException, CustomForbiddenException, CustomBadRequestException {
        UserTbl user = AuthenticateService.getCurrentUserFromSecurityContext();
        if (!user.isEnabled())
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can't access this api").field("user_status").build());

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new CustomNotFoundException(
                CustomError.builder().code("404").message("Project ID not found").build()));
        if (!project.getStatus().equals(ProjectStatus.APPROVED.toString())) {
            throw new CustomBadRequestException(
                    CustomError.builder().code("400").message("Project is not in time to invest").build());
        }
        if (project.getStartDate().after(new Date()) || project.getEndDate().before(new Date())) {
            throw new CustomBadRequestException(
                    CustomError.builder().code("400").message("Project is not in time to invest").build());
        }

        PersonalWallet generalWallet = userService.getPersonalWallet(user, WalletTypeString.GENERAL_WALLET);
        if (generalWallet.getBalance().doubleValue() < request.getTotalMoney().doubleValue()) {
            throw new CustomBadRequestException(CustomError.builder().code("400")
                    .message("Balance of wallet can't do this transaction because it is not enough for it").build());
        }
        if (project.getInvestmentTargetCapital().doubleValue() - project.getInvestedCapital().doubleValue() < request
                .getTotalMoney().doubleValue()) {
            throw new CustomBadRequestException(CustomError.builder().code("400")
                    .message("Balance of wallet can't do this transaction because it is over target of project")
                    .build());
        }
        ProjectWallet projectInvestmentWallet = projectWalletRepository
                .findByProject_ProjectIdAndWalletType_WalletTypeId(request.getProjectId(), WalletTypeString.PROJECT_INVESTMENT_WALLET.toString())
                .orElseThrow(() -> new CustomNotFoundException(
                        CustomError.builder().code("404").message("Project wallet not found").build()));
        generalWallet.setBalance(generalWallet.getBalance().subtract(request.getTotalMoney()));
        projectInvestmentWallet.setBalance(projectInvestmentWallet.getBalance().add(request.getTotalMoney()));

        Investment investment = Investment.builder()
                .investmentId(UUID.randomUUID())
                .investmentUser(user)
                .project(project)
                .totalMoney(request.getTotalMoney())
                .createdAt(new Date())
                .updatedAt(new Date())
                .contract("Link of contract")
                .status(InvestmentStatus.SUCCEED.toString())
                .shareAmount(request.getTotalMoney().divide(project.getInvestmentTargetCapital(), 5, 5))
                .build();
        investment = investmentRepository.save(investment);

        InvestmentTransaction investmentTransaction = InvestmentTransaction.builder()
                .amount(request.getTotalMoney())
                .investmentTransactioncreatedBy(user)
                .createdAt(new Date())
                .personalWallet(generalWallet).projectWallet(projectInvestmentWallet)
                .type(InvestmentTransactionType.INVESTOR_TO_PROJECT.toString())
                .investmentTransactioncreatedBy(user)
                .investment(investment)
                .description("Investor " + user.getFull_name() + " invested to project " + project.getProjectName())
                .build();
        investmentTransactionRepository.save(investmentTransaction);
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
