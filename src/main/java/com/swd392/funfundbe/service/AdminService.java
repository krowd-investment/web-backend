package com.swd392.funfundbe.service;

import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.CustomError;
import com.swd392.funfundbe.model.entity.Project;
import com.swd392.funfundbe.model.entity.ProjectWallet;
import com.swd392.funfundbe.model.entity.UserTbl;
import com.swd392.funfundbe.model.entity.WalletType;
import com.swd392.funfundbe.model.enums.LoginStatus;
import com.swd392.funfundbe.model.enums.ProjectStatus;
import com.swd392.funfundbe.model.enums.Role;
import com.swd392.funfundbe.model.enums.WalletTypeString;
import com.swd392.funfundbe.repository.ProjectRepository;
import com.swd392.funfundbe.repository.ProjectWalletRepository;
import com.swd392.funfundbe.repository.UserRepository;
import com.swd392.funfundbe.repository.WalletTypeRepository;
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
}
