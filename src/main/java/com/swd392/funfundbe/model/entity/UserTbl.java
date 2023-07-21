package com.swd392.funfundbe.model.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.swd392.funfundbe.model.enums.LoginStatus;
import jakarta.persistence.*;
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
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private RoleTbl role;
    private String full_name;
    private String email;
    private String phone;
    private String avatar;
    private String id_card;
    private String gender;
    private LocalDate birthdate;
    @Column(name = "tax_identification")
    private String taxIdentification;
    private String address;
    @Column(name = "bank_name")
    private String bankName;
    private String bank_account;
    private String momo;
    @Column(name = "created_at")
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private LoginStatus status;
    private boolean enabled;

    @OneToOne(mappedBy = "investor", cascade = CascadeType.ALL)
    private Investor investor;
    @OneToOne(mappedBy = "projectOwner", cascade = CascadeType.ALL)
    private ProjectOwner projectOwner;
    @OneToMany(mappedBy = "personalwalletOf", cascade = CascadeType.ALL)
    private List<PersonalWallet> personalWallet;
}
