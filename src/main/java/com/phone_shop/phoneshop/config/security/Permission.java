package com.phone_shop.phoneshop.config.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Generated;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Generated
public enum Permission {
    BRAND_READ("brand:read"),
    BRAND_WRITE("brand:write"),
    MODEL_READ("model:read"),
    MODEL_WRITE("model:write"),
    REPORT("report"),
    ;


    private String des;

    public String getDescription() {
        return this.des;
    }


}
