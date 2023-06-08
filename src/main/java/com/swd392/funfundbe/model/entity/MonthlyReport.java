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
@Table(name = "MonthlyReport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReport {
    @Id
    @Column(name = "monthly_report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int monthlyReportId;
    private BigDecimal revenue;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    private String report;
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "stage_id", referencedColumnName = "stage_id")
    private Stage stage;
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private UserTbl reportCreatedBy;
    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "user_id")
    private UserTbl reportUpdatedBy;

}
