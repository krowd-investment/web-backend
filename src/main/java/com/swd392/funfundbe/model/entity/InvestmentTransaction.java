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
@Table(name = "InvestmentTransaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "investment_transaction_id")
    private int investmentTransactionId;

    @ManyToOne
    @JoinColumn(name = "personal_wallet_id", referencedColumnName = "wallet_id", nullable = false)
    private PersonalWallet personalWallet;

    @ManyToOne
    @JoinColumn(name = "project_wallet_id", referencedColumnName = "project_wallet_id", nullable = false)
    private ProjectWallet projectWallet;

    @ManyToOne
    @JoinColumn(name = "investment_id", referencedColumnName = "investment_id")
    private Investment investment;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id", nullable = false)
    private UserTbl investmentTransactioncreatedBy;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "description", columnDefinition = "text")
    private String description;
}
