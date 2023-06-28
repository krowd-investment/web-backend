package com.swd392.funfundbe.model.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "PersonalWallet")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalWallet {
    @Id
    @Column(name = "wallet_id")
    private UUID walletId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserTbl personalwalletOf;

    @ManyToOne
    @JoinColumn(name = "wallet_type_id", referencedColumnName = "wallet_type_id")
    private WalletType walletType;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "created_at")
    private Date createdAt;
    @OneToMany(mappedBy = "fromWallet", cascade = CascadeType.ALL)
    private List<PersonalWalletTransaction> fromWalletTransactionList;
    @OneToMany(mappedBy = "toWallet", cascade = CascadeType.ALL)
    private List<PersonalWalletTransaction> toWalletTransactionList;
    @OneToMany(mappedBy = "personalWallet", cascade = CascadeType.ALL)
    private List<InvestmentTransaction> investmentTransactionCreatedByList;
}
