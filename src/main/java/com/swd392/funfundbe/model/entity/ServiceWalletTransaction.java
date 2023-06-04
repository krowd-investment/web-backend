package com.swd392.funfundbe.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ServiceWalletTransaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceWalletTransaction {
    @Id
    @Column(name = "service_wallet_transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceWalletTransactionId;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "service_wallet_description")
    private String serviceWalletDescription;
    @ManyToOne
    @JoinColumn(name = "service_wallet_id", referencedColumnName = "service_wallet_id")
    private ServiceWallet serviceWallet;
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private UserTbl serviceWalletTransactioncreatedBy;
}
