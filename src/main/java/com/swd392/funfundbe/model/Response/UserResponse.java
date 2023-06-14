package com.swd392.funfundbe.model.Response;

import com.swd392.funfundbe.model.entity.RoleTbl;
import com.swd392.funfundbe.model.entity.UserTbl;
import com.swd392.funfundbe.model.enums.LoginStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class UserResponse {
    private int userId;
    private String roleId;
    private String full_name;
    private String email;
    private String phone;
    private String avatar;
    private String id_card;
    private String gender;
    private LocalDate birthdate;
    private String taxIdentification;
    private String address;
    private String bankName;
    private String bank_account;
    private String momo;
    private Date createdAt;
    private LoginStatus status;
    private boolean enabled;

    public UserResponse(UserTbl user) {
        this.userId = user.getUserId();
        this.roleId = user.getRole().getRoleId();
        this.full_name = user.getFull_name();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.avatar = user.getAvatar();
        this.id_card = user.getId_card();
        this.gender = user.getGender();
        this.birthdate = user.getBirthdate();
        this.taxIdentification = user.getTaxIdentification();
        this.address = user.getAddress();
        this.bankName = user.getBankName();
        this.bank_account = user.getBank_account();
        this.momo = user.getMomo();
        this.createdAt = user.getCreatedAt();
        this.status = user.getStatus();
        this.enabled = user.isEnabled();
    }
}
