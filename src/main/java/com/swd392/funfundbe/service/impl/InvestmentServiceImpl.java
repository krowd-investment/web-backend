package com.swd392.funfundbe.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.CustomError;
import com.swd392.funfundbe.model.Request.InvestProjectRequest;
import com.swd392.funfundbe.model.Response.InvestedProjectResponse;
import com.swd392.funfundbe.model.entity.Investment;
import com.swd392.funfundbe.model.entity.InvestmentTransaction;
import com.swd392.funfundbe.model.entity.PersonalWallet;
import com.swd392.funfundbe.model.entity.PersonalWalletTransaction;
import com.swd392.funfundbe.model.entity.Project;
import com.swd392.funfundbe.model.entity.ProjectWallet;
import com.swd392.funfundbe.model.entity.UserTbl;
import com.swd392.funfundbe.model.entity.WalletType;
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
        PersonalWallet personalWallet = personalWalletRepository
                .findByPersonalwalletOf_UserIdAndWalletType_WalletTypeId(
                        AuthenticateService.getCurrentUserFromSecurityContext().getUserId(), "GENERAL_WALLET")
                .orElse(null);
        if (personalWallet != null) {
            personalWallet.setBalance(
                    new BigDecimal(personalWallet.getBalance().doubleValue() - request.getTotalMoney().doubleValue()));
            personalWalletRepository.save(personalWallet);
        }
        ProjectWallet projectWallet = projectWalletRepository
                .findByProject_ProjectIdAndWalletType_WalletTypeId(request.getProjectId(), "PROJECT_INVESTMENT_WALLET")
                .orElse(null);
        if (projectWallet != null) {
            projectWallet.setBalance(
                    new BigDecimal(projectWallet.getBalance().doubleValue() + request.getTotalMoney().doubleValue()));
            projectWalletRepository.save(projectWallet);
        }
        InvestmentTransaction investmentTransaction = InvestmentTransaction.builder().amount(request.getTotalMoney())
                .createdAt(new Date())
                .personalWallet(personalWallet).projectWallet(projectWallet)
                .type(InvestmentTransactionType.INVESTOR_TO_PROJECT.toString())
                .investmentTransactioncreatedBy(user)
                .investment(investment)
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
