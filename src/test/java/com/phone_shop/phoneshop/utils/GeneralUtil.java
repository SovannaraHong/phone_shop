package com.phone_shop.phoneshop.utils;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneralUtil {

    @Test
    public void sendPass() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String nara123 = encoder.encode("nara123");
        System.out.println(nara123);
    }
}
