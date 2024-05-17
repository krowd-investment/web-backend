package com.swd392.funfundbe.model.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ServiceWallet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceWallet {
    @Id
    @Column(name = "service_wallet_id")
    private UUID serviceWalletId;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "created_at")
    private Date createAt;
    private boolean status;
    @OneToMany(mappedBy = "serviceWallet", cascade = CascadeType.ALL)
    private List<ServiceWalletTransaction> serviceWalletTransactionList;
    @OneToOne
    @JoinColumn(name = "investment_id", referencedColumnName = "investment_id", foreignKey = @ForeignKey(name = "FK_investment_id"))
    private Investment investment;

    @ManyToOne
    @JoinColumn(name = "wallet_type_id", referencedColumnName = "wallet_type_id")
    private WalletType walletType;
    @ManyToOne
    @JoinColumn(name = "stage_id", referencedColumnName = "stage_id")
    private Stage stage;

}
