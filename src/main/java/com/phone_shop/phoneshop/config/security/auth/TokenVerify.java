package com.phone_shop.phoneshop.config.security.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

// filter step2
public class TokenVerify extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (Objects.isNull(authorization) || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization.replace("Bearer ", "");
//        String secreteKey = "hghsdghowrhoew234sdjhgfskhjgdjfsdkfjlsdhfiwoeytiweuyt4564356455744grgdfk4654lhskdfh35";
        Jws<Claims> claimsJws = Jwts.parserBuilder()
//                .setSigningKey(Keys.hmacShaKeyFor(secreteKey.getBytes()))
                .setSigningKey(JwtUtil.KEY)
                .build()
                .parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        String username = body.getSubject();
        List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");

        Set<SimpleGrantedAuthority> grantedAuthorities =
                authorities
                        .stream()
                        .map(auth -> new SimpleGrantedAuthority(auth.get("authority")))
                        .collect(Collectors.toSet());
        // use this for let spring remember and need authentication
        Authentication auth = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}
