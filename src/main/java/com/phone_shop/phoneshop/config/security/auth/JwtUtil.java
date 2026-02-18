package com.phone_shop.phoneshop.config.security.auth;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtUtil {

    private static final String SECRET =
            "hghsdghowrhoew234sdjhgfskhjgdjfsdkfjlsdhfiwoeytiweuyt4564356455744grgdfk4654lhskdfh35";

    public static final SecretKey KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
}
