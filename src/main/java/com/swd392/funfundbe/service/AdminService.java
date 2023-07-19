package com.swd392.funfundbe.service;

import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.CustomError;
import com.swd392.funfundbe.model.entity.*;
import com.swd392.funfundbe.model.enums.*;
import com.swd392.funfundbe.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final ProjectRepository projectRepository;
    private final WalletTypeRepository walletTypeRepository;
    private final ProjectWalletRepository projectWalletRepository;
    private final InvestmentTransactionRepo investmentTransactionRepo;

    private UserTbl checkPOForConfirmation(Integer poId) throws CustomNotFoundException, CustomBadRequestException {
        String poRole = Role.PO.toString();
        UserTbl po = userRepository.findByUserIdAndRole_RoleId(poId, poRole)
                .orElseThrow(() -> new CustomNotFoundException(
                        CustomError.builder().code("404").message("PO ID not found").build()
                ));
        if (po.getStatus().equals(LoginStatus.FILLING_REQUIRED))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400")
                            .message("PO needs to register information before approval").build()
            );
        if (po.getStatus().equals(LoginStatus.DELETED))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400")
                            .message("PO has been banned").build()
            );
        if (po.getStatus().equals(LoginStatus.APPROVED))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400")
                            .message("PO has been approved").build()
            );
        return po;
    }
    public void approvePO(Integer poId) throws CustomNotFoundException, CustomBadRequestException {
        UserTbl po = checkPOForConfirmation(poId);
        po.setStatus(LoginStatus.APPROVED);
        po.setEnabled(true);
        userService.createWallet(po, WalletTypeString.GENERAL_WALLET);
        userService.createWallet(po, WalletTypeString.COLLECTION_WALLET);
    }

    public void rejectPO(Integer poId) throws CustomNotFoundException, CustomBadRequestException {
        UserTbl po = checkPOForConfirmation(poId);
        po.setStatus(LoginStatus.REJECTED);
        po.setEnabled(false);
    }

    public void approveProject(Integer projectId) throws CustomNotFoundException, CustomBadRequestException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomNotFoundException(
                        CustomError.builder()
                                .code("404").message("Project ID not found")
                                .build()
        ));
        checkProjectStatus(project);

        project.setStatus(ProjectStatus.APPROVED.toString());

        createProjectWallet(project, WalletTypeString.PROJECT_INVESTMENT_WALLET);
        createProjectWallet(project, WalletTypeString.PROJECT_PAYMENT_WALLET);
    }

    public void rejectProject(Integer projectId) throws CustomNotFoundException, CustomBadRequestException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomNotFoundException(
                        CustomError.builder()
                                .code("404").message("Project ID not found")
                                .build()
                ));
        checkProjectStatus(project);

        project.setStatus(ProjectStatus.REJECTED.toString());
    }

    private void checkProjectStatus(Project project) throws CustomBadRequestException {
        if (project.getStatus().equals(ProjectStatus.APPROVED.toString()))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400").message("Project has been approved")
                            .build()
            );
        if (project.getStatus().equals(ProjectStatus.STARTED.toString()))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400").message("Project has been started")
                            .build()
            );
        if (project.getStatus().equals(ProjectStatus.CANCELED.toString()))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400").message("Project has been canceled")
                            .build()
            );
        if (project.getStatus().equals(ProjectStatus.READY.toString()))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400").message("Project has been ready for starting")
                            .build()
            );
        if (project.getStatus().equals(ProjectStatus.DONE.toString()))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400").message("Project has been done")
                            .build()
            );
    }
    private void createProjectWallet(Project project, WalletTypeString walletTypeString) throws CustomNotFoundException {
        WalletType walletType = walletTypeRepository.findById(walletTypeString.toString())
                .orElseThrow(() -> new CustomNotFoundException(
                        CustomError.builder()
                                .code("404").message("Wallet type not found")
                                .build()
                ));
        ProjectWallet projectWallet = ProjectWallet.builder()
                .projectWalletId(UUID.randomUUID())
                .walletType(walletType)
                .project(project)
                .balance(BigDecimal.ZERO)
                .updatedAt(new Date())
                .status(true)
                .build();
        projectWalletRepository.save(projectWallet);
    }

    public void approveInvestedCapitalOfProject(int projectId) throws CustomNotFoundException, CustomBadRequestException {
        System.out.println(projectId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomNotFoundException(CustomError.builder().message("Project ID not found").build()));
        checkProjectStatusForApprovingInvestedCapital(project);

        ProjectWallet projectInvestmentWallet = project.getProjectWalletList().stream()
                .filter(projectWallet -> projectWallet.getWalletType().getWalletTypeId().equals(WalletTypeString.PROJECT_INVESTMENT_WALLET.toString()))
                .toList().get(0);
        PersonalWallet collectionWallet = project.getProjectCreatedBy().getPersonalWallet().stream()
                .filter(personalWallet -> personalWallet.getWalletType().getWalletTypeId().equals(WalletTypeString.COLLECTION_WALLET.toString()))
                .toList().get(0);

        BigDecimal investedCapital = projectInvestmentWallet.getBalance();
        projectInvestmentWallet.setBalance(BigDecimal.ZERO);
        projectInvestmentWallet.setUpdatedAt(new Date());
        collectionWallet.setBalance(collectionWallet.getBalance().add(investedCapital));
        InvestmentTransaction investmentTransaction = InvestmentTransaction.builder()
                .personalWallet(collectionWallet)
                .projectWallet(projectInvestmentWallet)
                .investment(null)
                .type(InvestmentTransactionType.PROJECT_TO_PO.toString())
                .investmentTransactioncreatedBy(AuthenticateService.getCurrentUserFromSecurityContext())
                .createdAt(new Date())
                .amount(investedCapital)
                .description("Approve invested capital and transfer money to PO's collection wallet")
                .build();
        investmentTransactionRepo.save(investmentTransaction);
        project.setStatus(ProjectStatus.READY.toString());
    }

    private void checkProjectStatusForApprovingInvestedCapital(Project project) throws CustomBadRequestException {
        if (project.getStatus().equals(ProjectStatus.REJECTED.toString()))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400").message("Project has been rejected")
                            .build()
            );
        if (project.getStatus().equals(ProjectStatus.STARTED.toString()))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400").message("Project has been started")
                            .build()
            );
        if (project.getStatus().equals(ProjectStatus.CANCELED.toString()))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400").message("Project has been canceled")
                            .build()
            );
        if (project.getStatus().equals(ProjectStatus.READY.toString()))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400").message("Project has been ready for starting")
                            .build()
            );
        if (project.getStatus().equals(ProjectStatus.DONE.toString()))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400").message("Project has been done")
                            .build()
            );
    }
}
