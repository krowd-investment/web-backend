package com.swd392.funfundbe.model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticateResponse {
    private Integer userId;
    private String status;
    private boolean enabled;
}
