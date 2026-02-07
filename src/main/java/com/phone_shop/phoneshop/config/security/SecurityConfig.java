package com.phone_shop.phoneshop.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "index.html", "components/**").permitAll()
                        .requestMatchers("/**").hasRole("ADMIN")
                        .requestMatchers("/brands/**").hasRole("SALE")
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(Customizer.withDefaults())


        ;
        return http.build();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("Sovannara")
                .password(passwordEncoder.encode("nara123"))
                .roles("ADMIN")
                .build();
        UserDetails user2 = User.builder()
                .username("Thida")
                .password(passwordEncoder.encode("thida123"))
                .roles("SALE")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
