package com.onlineshop.user.rest.configurations;

import com.onlineshop.user.core.security.jwt.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .addFilterAfter(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/v2/api-docs",
                                                  "/v3/api-docs",
                                                  "/v3/api-docs/**",
                                                  "/swagger-resources",
                                                  "/swagger-resources/**",
                                                  "/configuration/ui",
                                                  "/configuration/security",
                                                  "/swagger-ui/**",
                                                  "/webjars/**",
                                                  "/swagger-ui.html").permitAll()
                        .anyRequest()
                        .authenticated())
               .build();
    }

}
