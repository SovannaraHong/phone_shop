package com.phone_shop.phoneshop.config.security;

import com.phone_shop.phoneshop.config.security.auth.JwtLoginFilter;
import com.phone_shop.phoneshop.config.security.auth.TokenVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager manager, AuthenticationProvider provider) throws Exception {

        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(manager);
        jwtLoginFilter.setFilterProcessesUrl("/login");
        http.csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(provider)
                .authorizeHttpRequests(auth -> auth

                                .requestMatchers("/", "index.html", "/auth/register", "/role/**").permitAll()
//                                .requestMatchers(HttpMethod.POST, "/brands").hasAuthority(BRAND_WRITE.getDescription())
//                                .requestMatchers(HttpMethod.GET, "/models/**").hasAuthority(MODEL_READ.getDescription())
//                                .requestMatchers(HttpMethod.GET, "/reports/**").hasAuthority(REPORT.getDescription())
//                                  .requestMatchers("/**").hasRole("ADMIN")
//                                .requestMatchers("/brands").hasRole("STOCKER")
//                                .requestMatchers("/reports/**").hasRole("SALE")

                                .anyRequest()
                                .authenticated()
                )
                .addFilter(jwtLoginFilter)
                .addFilterAfter(new TokenVerify(), JwtLoginFilter.class);


//                .httpBasic(Customizer.withDefaults());
        return http.build();

    }


    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    /*
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("nara")
                .password(passwordEncoder.encode("nara123"))
                .authorities(RoleEnum.ADMIN.getSimpleGrantedAuthority())
//                .roles("ADMIN")
                .build();
        UserDetails user2 = User.builder()
                .username("thida")
                .password(passwordEncoder.encode("thida123"))
                .authorities(RoleEnum.SALE.getSimpleGrantedAuthority())
//                .roles("SALE")
                .build();
        UserDetails user3 = User.builder()
                .username("lyka")
                .password(passwordEncoder.encode("lyka123"))
                .authorities(RoleEnum.STOCKER.getSimpleGrantedAuthority())
//                .roles("STOCKER")
                .build();
        return new InMemoryUserDetailsManager(user1, user2, user3);
    }
    */


}


