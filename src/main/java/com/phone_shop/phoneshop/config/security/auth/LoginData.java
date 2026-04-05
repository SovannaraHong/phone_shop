package com.phone_shop.phoneshop.config.security.auth;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginData {

    private Long userId;
    private String username;
    private List<String> roles;
    private String accessToken;
    private String refreshToken;

}