package com.example.userauthservice_june2025.confuguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().disable(); //  accessing website with other website resources like scaler is showing GFG website image
        httpSecurity.csrf().disable(); // It is similar to cors
        httpSecurity.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        // THis will not spot any api call because of spring security

        return httpSecurity.build();
    }
}
