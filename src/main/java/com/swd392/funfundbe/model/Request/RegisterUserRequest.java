package com.swd392.funfundbe.model.Request;

import com.swd392.funfundbe.model.enums.Gender;
import com.swd392.funfundbe.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserRequest {
    private Role roleId;
    @NotBlank
    private String fullName;
    @NotBlank
    private String phone;
    private String avatar;
    @NotBlank
    private String id_card;
    private Gender gender;
    private LocalDate birthdate;
    @NotBlank
    private String taxIdentification;
    private String address;
    private String bankName;
    private String bankAccount;
    private String momo;
}
