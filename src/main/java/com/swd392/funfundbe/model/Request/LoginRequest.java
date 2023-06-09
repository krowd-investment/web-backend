package com.swd392.funfundbe.model.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @Schema(description = "For login via google")
    private String idToken;
    @Schema(description = "For login and verify OTP via phone number")
    private String phone;
    @Schema(description = "For verify OTP via phone number")
    private String otp;

}
