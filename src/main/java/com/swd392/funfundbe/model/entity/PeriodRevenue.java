package com.swd392.funfundbe.model.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "PeriodRevenue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeriodRevenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "period_revenue_id")
    private int periodRevenueId;
    @ManyToOne
    @JoinColumn(name = "stage_id", referencedColumnName = "stage_id", nullable = false)
    private Stage stage;

    @Column(name = "actual_amount", nullable = false)
    private BigDecimal actualAmount;

    @Column(name = "shared_amount", nullable = false)
    private BigDecimal sharedAmount;

    @Column(name = "paid_amount", nullable = false)
    private BigDecimal paidAmount;

    @Column(name = "optimistic_expected_amount", nullable = false)
    private BigDecimal optimisticExpectedAmount;

    @Column(name = "pessimistic_expected_amount", nullable = false)
    private BigDecimal pessimisticExpectedAmount;

    @Column(name = "normal_expected_amount", nullable = false)
    private BigDecimal normalExpectedAmount;

    @Column(name = "optimistic_expected_ratio", nullable = false)
    private BigDecimal optimisticExpectedRatio;

    @Column(name = "pessimistic_expected_ratio", nullable = false)
    private BigDecimal pessimisticExpectedRatio;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id", nullable = false)
    private UserTbl periodRevenueCreatedBy;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "user_id", nullable = false)
    private UserTbl periodRevenueUpdatedBy;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Column(name = "status", nullable = false)
    private boolean status;
    @OneToMany(mappedBy = "periodRevenue", cascade = CascadeType.ALL)
    private List<Payment> paymentList;
}
