package com.phone_shop.phoneshop.config.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phone_shop.phoneshop.config.security.AuthUser;
import com.phone_shop.phoneshop.payload.request.LoginRequest;
import com.phone_shop.phoneshop.payload.response.LoginResponse;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


//filter step 1
//create token
@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    //Reads username/password from request body
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            LoginRequest loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);

            Authentication authRequest =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

            Authentication authenticate = authenticationManager.authenticate(authRequest);
            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    //If authentication is successful → successfulAuthentication() runs

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        String secreteKey = "hghsdghowrhoew234sdjhgfskhjgdjfsdkfjlsdhfiwoeytiweuyt4564356455744grgdfk4654lhskdfh35";
        AuthUser user = (AuthUser) authResult.getPrincipal();
        long UserId = user.getId();

//        String token = Jwts.builder()
//                .setSubject(authResult.getName())
//                .claim("authorities", authResult.getAuthorities())
//                .setIssuedAt(new Date())
//                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(7)))
//                .setIssuer("phone_shop")
////                .signWith(Keys.hmacShaKeyFor(secreteKey.getBytes()))
//                .signWith(JwtUtil.KEY, io.jsonwebtoken.SignatureAlgorithm.HS512)
//                .compact();
//        response.setHeader("Authorization", "Bearer " + token);

        String accessToken = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(7)))
                .setIssuer("phone_shop")
                .signWith(JwtUtil.KEY, io.jsonwebtoken.SignatureAlgorithm.HS512)
                .compact();
        String refreshToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(30)))
                .setIssuer("phone_shop")
                .signWith(JwtUtil.KEY, io.jsonwebtoken.SignatureAlgorithm.HS512)
                .compact();
        LoginData data = LoginData.builder()
                .userId(UserId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        LoginResponse loginResponse = LoginResponse.builder()
                .status(200)
                .message("Login success")
                .data(data)
                .timestamp(LocalDateTime.now().toString())
                .build();
        response.setContentType("application/json");
        try {
            new ObjectMapper()
                    .writeValue(response.getOutputStream(), loginResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        LoginResponse errorResponse = LoginResponse.builder()
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .message("Unauthorized || invalid username or password")
                .data(null)
                .timestamp(LocalDateTime.now().toString())
                .build();

        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
    }
}
