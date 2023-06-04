package com.swd392.funfundbe.model.entity;

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
@Table(name = "Stage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stage {
    @Id
    @Column(name = "stage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stageId;
    private String name;
    private String description;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "is_over_due")
    private boolean isOverDue;
    private boolean status;
    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL)
    private List<MonthlyReport> monthlyReportList;
    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL)
    private List<ServiceWallet> serviceWalletList;
    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL)
    private List<PeriodRevenue> periodRevenueList;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;

}
