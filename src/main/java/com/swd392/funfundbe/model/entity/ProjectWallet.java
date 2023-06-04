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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ProjectWallet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectWallet {
    @Id
    @Column(name = "project_wallet_id")
    private UUID projectWalletId;

    @ManyToOne
    @JoinColumn(name = "wallet_type_id", referencedColumnName = "wallet_type_id", nullable = false)
    private WalletType walletType;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false)
    private Project project;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status", nullable = false)
    private boolean status;
    @OneToMany(mappedBy = "projectWallet", cascade = CascadeType.ALL)
    private List<InvestmentTransaction> investmentTransactionCreatedByList;
}
