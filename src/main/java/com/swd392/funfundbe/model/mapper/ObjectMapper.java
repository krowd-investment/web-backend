package com.swd392.funfundbe.model.mapper;

import java.util.List;

import com.swd392.funfundbe.model.Request.InvestProjectRequest;
import com.swd392.funfundbe.model.Response.*;
import com.swd392.funfundbe.model.Response.transaction.DepositTransactionHistory;
import com.swd392.funfundbe.model.Response.transaction.InvestmentTransactionHistory;
import com.swd392.funfundbe.model.Response.transaction.PersonalWalletTransactionHistory;
import com.swd392.funfundbe.model.Response.transaction.TransactionHistory;
import com.swd392.funfundbe.model.entity.*;
import com.swd392.funfundbe.model.enums.InvestmentTransactionType;
import com.swd392.funfundbe.model.enums.TransactionHistoryType;
import com.swd392.funfundbe.model.enums.WalletTypeString;

public class ObjectMapper {
    public static ProjectResponse fromProjectToProjectResponse(Project project) {
        return ProjectResponse.builder().projectId(project.getProjectId())
                .duration(project.getDuration())
                .mutiplier(project.getMultiplier())
                .image(project.getImage())
                .projectName(project.getProjectName())
                .targetCapital(project.getInvestmentTargetCapital())
                .capital(project.getInvestedCapital())
                .sharedRevenue(project.getSharedRevenue())
                .brand(project.getBrand())
                .endDate(project.getEndDate())
                .startDate(project.getStartDate())
                .status(project.getStatus())
                .numberOfStage(project.getNumberOfStage())
                .projectDescription(project.getProjectDescription())
                .investmentTargetCapital(project.getInvestmentTargetCapital())
                .paidAmount(project.getPaidAmount())
                .remainingAmount(project.getRemainingAmount())
                .businessLicense(project.getBusinessLicense())
                .fieldId(project.getField().getFieldId())
                .fieldName(project.getField().getName())
                .areaId(project.getArea().getAreaId())
                .areaName(project.getArea().getCity() + "-" + project.getArea().getDistrict())
                .build();
    }

    public static ProjectDetailResponse fromProjectToProjectDetailResponse(Project project) {
        return ProjectDetailResponse.builder()
                .projectId(project.getProjectId())
                .duration(project.getDuration())
                .mutiplier(project.getMultiplier())
                .image(project.getImage())
                .projectName(project.getProjectName())
                .targetCapital(project.getInvestmentTargetCapital())
                .capital(project.getInvestedCapital())
                .sharedRevenue(project.getSharedRevenue())
                .brand(project.getBrand())
                .endDate(project.getEndDate())
                .startDate(project.getStartDate())
                .status(project.getStatus())
                .numberOfStage(project.getNumberOfStage())
                .projectDescription(project.getProjectDescription())
                .investmentTargetCapital(project.getInvestmentTargetCapital())
                .paidAmount(project.getPaidAmount())
                .remainingAmount(project.getRemainingAmount())
                .businessLicense(project.getBusinessLicense())
                .fieldId(project.getField().getFieldId())
                .fieldName(project.getField().getName())
                .areaId(project.getArea().getAreaId())
                .areaName(project.getArea().getCity() + "-" + project.getArea().getDistrict())
                .stages(project.getStageList().stream().map(ObjectMapper::fromStageToStageResponse).toList())
                .build();
    }

    public static InvestedProjectResponse fromInvestmentToInvestedResponse(Investment investment) {
        return investment == null ? null :
                InvestedProjectResponse.builder()
                        .investmentId(investment.getInvestmentId())
                        .projectId(investment.getProject().getProjectId())
                        .projectName(investment.getProject().getProjectName())
                        .mulplier(investment.getProject().getMultiplier())
                        .image(investment.getProject().getImage())
                        .totalMoney(investment.getTotalMoney())
                        .createAt(investment.getCreatedAt())
                        .updateAt(investment.getUpdatedAt())
                        .status(investment.getStatus())
                        .shareAmount(investment.getShareAmount())
                        .build();
    }

    public static FieldResponse fromFieldToFieldResponse(Field field) {
        if (field == null)
            return null;
        FieldResponse fieldResponse = FieldResponse.builder()
                .fieldId(field.getFieldId())
                .name(field.getName())
                .fieldDescription(field.getFieldDescription())
                .status(field.isStatus())
                .build();
        return fieldResponse;
    }

    public static AreaResponse fromAreaToAreaResponse(Area area) {
        if (area == null)
            return null;
        AreaResponse areaResponse = AreaResponse.builder()
                .areaId(area.getAreaId())
                .city(area.getCity())
                .district(area.getDistrict())
                .details(area.getDetails())
                .status(area.isStatus())
                .build();
        return areaResponse;
    }

    public static ProjectWalletResponse fromProjectWalletToProjectWalletResponse(ProjectWallet projectWallet) {
        if (projectWallet == null) return null;
        return ProjectWalletResponse.builder()
                .projectWalletId(projectWallet.getProjectWalletId())
                .walletTypeId(projectWallet.getWalletType().getWalletTypeId())
                .projectId(projectWallet.getProject().getProjectId())
                .balance(projectWallet.getBalance())
                .updatedAt(projectWallet.getUpdatedAt())
                .status(projectWallet.isStatus())
                .build();
    }

    public static StageResponse fromStageToStageResponse(Stage stage) {
        return stage == null ? null :
                StageResponse.builder()
                        .stageId(stage.getStageId())
                        .name(stage.getName())
                        .description(stage.getDescription())
                        .startDate(stage.getStartDate())
                        .endDate(stage.getEndDate())
                        .isOverDue(stage.isOverDue())
                        .projectId(stage.getProject().getProjectId())
                        .stageStatus(stage.getStageStatus())
                        .build();
    }

    public static TransactionHistory toTransactionHistory(PersonalWallet generalWallet,
                                                          PersonalWallet collectionWallet,
                                                          List<DepositTransaction> depositTransaction,
                                                          List<PersonalWalletTransaction> personalWalletTransaction,
                                                          List<InvestmentTransaction> investmentTransaction,
                                                          TransactionHistoryType type) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setGeneralWalletId(generalWallet.getWalletId());
        transactionHistory.setCollectionWalletId(collectionWallet.getWalletId());

        if (type.equals(TransactionHistoryType.DEPOSIT))
            transactionHistory.setDepositTransactionHistory(depositTransaction == null ? null :
                    depositTransaction.stream().map(ObjectMapper::toDepositTransactionHistory).toList()
            );
        if (type.equals(TransactionHistoryType.GENERAL_TO_COLLECTION))
            transactionHistory.setFromGeneralToCollectionWalletTransaction(personalWalletTransaction == null ? null :
                    personalWalletTransaction.stream()
                            .filter(transaction -> transaction.getFromWallet().getWalletType().getWalletTypeId().equals(WalletTypeString.GENERAL_WALLET.toString()))
                            .map(ObjectMapper::toPersonalWalletTransactionHistory)
                            .toList()
            );
        if (type.equals(TransactionHistoryType.COLLECTION_TO_GENERAL))
            transactionHistory.setFromCollectionToGeneralWalletTransaction(personalWalletTransaction == null ? null :
                    personalWalletTransaction.stream()
                            .filter(transaction -> transaction.getFromWallet().getWalletType().getWalletTypeId().equals(WalletTypeString.COLLECTION_WALLET.toString()))
                            .map(ObjectMapper::toPersonalWalletTransactionHistory)
                            .toList()
            );
        if (type.equals(TransactionHistoryType.PERSONAL_TO_PROJECT))
            transactionHistory.setFromPersonalToProjectWallet(investmentTransaction == null ? null :
                    investmentTransaction.stream()
                            .filter(transaction ->
                                    transaction.getType().equals(InvestmentTransactionType.INVESTOR_TO_PROJECT.toString())
                                    || transaction.getType().equals(InvestmentTransactionType.PO_TO_PROJECT.toString())
                            )
                            .map(ObjectMapper::toInvestmentTransactionHistory)
                            .toList()
            );
        if (type.equals(TransactionHistoryType.PROJECT_TO_PERSONAL))
            transactionHistory.setFromProjectToPersonalWallet(investmentTransaction == null ? null :
                    investmentTransaction.stream()
                            .filter(transaction ->
                                    transaction.getType().equals(InvestmentTransactionType.PROJECT_TO_INVESTOR.toString())
                                            || transaction.getType().equals(InvestmentTransactionType.PROJECT_TO_PO.toString())
                            )
                            .map(ObjectMapper::toInvestmentTransactionHistory)
                            .toList()
            );
        return transactionHistory;

    }

    public static DepositTransactionHistory toDepositTransactionHistory(DepositTransaction depositTransaction) {
        if (depositTransaction == null) return null;
        return DepositTransactionHistory.builder()
                .depositTransactionId(depositTransaction.getDepositTransactionId())
                .toWalletId(depositTransaction.getToWallet().getWalletId())
                .createdAt(depositTransaction.getCreatedAt())
                .amount(depositTransaction.getAmount())
                .build();
    }

    public static PersonalWalletTransactionHistory toPersonalWalletTransactionHistory(PersonalWalletTransaction personalWalletTransaction) {
        return personalWalletTransaction == null ? null
                : PersonalWalletTransactionHistory.builder()
                .personalWalletTransactionId(personalWalletTransaction.getPersonalWalletTransactionId())
                .fromWalletId(personalWalletTransaction.getFromWallet().getWalletId())
                .toWalletId(personalWalletTransaction.getToWallet().getWalletId())
                .createdAt(personalWalletTransaction.getCreatedAt())
                .amount(personalWalletTransaction.getAmount())
                .fee(personalWalletTransaction.getFee())
                .description(personalWalletTransaction.getPersonalWalletDescription())
                .build();
    }

    public static InvestmentTransactionHistory toInvestmentTransactionHistory(InvestmentTransaction investmentTransaction) {
        return investmentTransaction == null ? null :
                InvestmentTransactionHistory.builder()
                        .investmentTransactionId(investmentTransaction.getInvestmentTransactionId())
                        .personalWalletId(investmentTransaction.getPersonalWallet().getWalletId())
                        .projectWalletId(investmentTransaction.getProjectWallet().getProjectWalletId())
                        .type(investmentTransaction.getType())
                        .createdAt(investmentTransaction.getCreatedAt())
                        .amount(investmentTransaction.getAmount())
                        .description(investmentTransaction.getDescription())
                        .build();
    }
}
