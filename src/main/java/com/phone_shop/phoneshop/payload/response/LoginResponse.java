package com.phone_shop.phoneshop.payload.response;

import com.phone_shop.phoneshop.config.security.auth.LoginData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private int status;
    private String message;
    private LoginData data;
    private String timestamp;

}