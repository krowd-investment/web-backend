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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Investment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Investment {
    @Id
    @Column(name = "investment_id")
    private UUID investmentId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserTbl investmentUser;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;
    @Column(name = "total_money")
    private BigDecimal totalMoney;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    private String contract;
    @Column(name = "status", length = 50, nullable = false)
    private String status;
    @OneToOne(mappedBy = "investment", cascade = CascadeType.ALL)
    private ServiceWallet serviceWallet;
    @OneToMany(mappedBy = "investment", cascade = CascadeType.ALL)
    private List<InvestmentTransaction> investmentTransactionList;
    @OneToMany(mappedBy = "investment", cascade = CascadeType.ALL)
    private List<Payment> paymentList;
}
