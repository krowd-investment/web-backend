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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Usertbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTbl {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private RoleTbl role;
    private String full_name;
    private String email;
    private String phone;
    private String avatar;
    private String id_card;
    private String gender;
    private Date birthdate;
    @Column(name = "tax_identification")
    private String taxIndentification;
    private String address;
    @Column(name = "bank_name")
    private String bankName;
    private String bank_account;
    private String momo;
    @Column(name = "create_at")
    private Date createdAt;
    private String status;
    private boolean enabled;

    @OneToOne(mappedBy = "investor", cascade = CascadeType.ALL)
    private Investor investor;
    @OneToOne(mappedBy = "projectOwner", cascade = CascadeType.ALL)
    private ProjectOwner projectOwner;
    @OneToMany(mappedBy = "personalwalletOf", cascade = CascadeType.ALL)
    private List<PersonalWallet> personalWallet;
    @OneToMany(mappedBy = "projectCreatedBy", cascade = CascadeType.ALL)
    private List<Project> projectCreatedBy;
    @OneToMany(mappedBy = "projectApprovedBy", cascade = CascadeType.ALL)
    private List<Project> projectApprovedBy;
    @OneToMany(mappedBy = "projectUpdatedBy", cascade = CascadeType.ALL)
    private List<Project> projectUpdatedBy;
    @OneToMany(mappedBy = "investmentUser", cascade = CascadeType.ALL)
    private List<Investment> investmentsList;
    @OneToMany(mappedBy = "reportCreatedBy", cascade = CascadeType.ALL)
    private List<MonthlyReport> reportCreatedBy;
    @OneToMany(mappedBy = "reportUpdatedBy", cascade = CascadeType.ALL)
    private List<MonthlyReport> reportUpdatedBy;
    @OneToMany(mappedBy = "pWTransactionrcreatedBy", cascade = CascadeType.ALL)
    private List<PersonalWalletTransaction> personalTransactionCreatedByList;
    @OneToMany(mappedBy = "serviceWalletTransactioncreatedBy", cascade = CascadeType.ALL)
    private List<ServiceWalletTransaction> serviceTransactionCreatedByList;
    @OneToMany(mappedBy = "investmentTransactioncreatedBy", cascade = CascadeType.ALL)
    private List<InvestmentTransaction> investmentTransactionCreatedByList;
    @OneToMany(mappedBy = "periodRevenueCreatedBy", cascade = CascadeType.ALL)
    private List<PeriodRevenue> periodRevenueCreatedByList;
    @OneToMany(mappedBy = "periodRevenueUpdatedBy", cascade = CascadeType.ALL)
    private List<PeriodRevenue> periodRevenueUpdatedByList;
}
