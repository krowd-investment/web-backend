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
@Table(name = "Payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int paymentId;

    @ManyToOne
    @JoinColumn(name = "period_revenue_id", referencedColumnName = "period_revenue_id", nullable = false)
    private PeriodRevenue periodRevenue;

    @ManyToOne
    @JoinColumn(name = "investment_id", referencedColumnName = "investment_id", nullable = false)
    private Investment investment;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "status", nullable = false)
    private boolean status;
}
