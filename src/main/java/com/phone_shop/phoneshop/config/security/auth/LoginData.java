package com.phone_shop.phoneshop.config.security.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginData {

    private Long userId;
    private String accessToken;
    private String refreshToken;

}