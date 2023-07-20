package com.swd392.funfundbe.model.Response.transaction;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionHistory {
    private UUID generalWalletId;
    private UUID collectionWalletId;
    private List<DepositTransactionHistory> depositTransactionHistory;
    private List<PersonalWalletTransactionHistory> fromGeneralToCollectionWalletTransaction;
    private List<PersonalWalletTransactionHistory> fromCollectionToGeneralWalletTransaction;
    private List<InvestmentTransactionHistory> fromProjectToPersonalWallet;
    private List<InvestmentTransactionHistory> fromPersonalToProjectWallet;
}
