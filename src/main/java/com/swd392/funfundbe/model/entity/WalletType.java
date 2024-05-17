package com.swd392.funfundbe.model.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "WalletType")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletType {
    @Id
    @Column(name ="wallet_type_id")
    private String walletTypeId;
    @Column(name ="wallet_type_name")
    private String walleTypeName;
    @Column(name ="wallet_type_description")
    private String walletTypeDescription;
    private String mode;
    @OneToMany(mappedBy = "walletType", cascade = CascadeType.ALL)
    private List<PersonalWallet> personalWallet;
    @OneToMany(mappedBy = "walletType", cascade = CascadeType.ALL)
    private List<ServiceWallet> serviceWalletList;
    @OneToMany(mappedBy = "walletType", cascade = CascadeType.ALL)
    private List<ProjectWallet> projectWalletList;

}
