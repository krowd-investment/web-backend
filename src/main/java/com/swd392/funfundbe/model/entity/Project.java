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
@Table(name = "Project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;
    @Column(name = "investment_target_capital")
    private BigDecimal investmentTargetCapital;
    @Column(name = "invested_capital")
    private BigDecimal investedCapital;
    @Column(name = "shared_revenue")
    private BigDecimal sharedRevenue;
    @Column(name = "multiplier")
    private BigDecimal multiplier;
    @Column(name = "paid_amount")
    private BigDecimal paidAmount;
    @Column(name = "remaining_amount")
    private BigDecimal remainingAmount;
    @Column(name = "number_of_stage")
    private int numberOfStage;
    @Column(name = "duration")
    private int duration;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "image")
    private String image;
    @Column(name = "project_description")
    private String projectDescription;
    @Column(name = "business_license")
    private String businessLicense;
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Risk> riskList;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Investment> investmentList;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Stage> stageList;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectWallet> projectWalletList;
    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "user_id")
    private UserTbl projectUpdatedBy;

    @ManyToOne
    @JoinColumn(name = "field_id", referencedColumnName = "field_id")
    private Field field;

    @ManyToOne
    @JoinColumn(name = "area_id", referencedColumnName = "area_id")
    private Area area;
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private UserTbl projectCreatedBy;

    @ManyToOne
    @JoinColumn(name = "approved_by", referencedColumnName = "user_id")
    private UserTbl projectApprovedBy;
}
