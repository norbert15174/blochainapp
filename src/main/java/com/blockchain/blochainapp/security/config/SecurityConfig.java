package com.blockchain.blochainapp.security.config;

import com.blockchain.blochainapp.data.functionality.user.service.UserQueryService;
import com.blockchain.blochainapp.security.cache.CurrentSessionCache;
import com.blockchain.blochainapp.security.filter.JwtAuthenticationFilter;
import com.blockchain.blochainapp.security.handler.RestUnauthorizedHandler;
import com.blockchain.blochainapp.security.service.JwtManageService;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtManageService jwtManageService;
    private final UserQueryService userQueryService;
    private final CurrentSessionCache currentSessionCache;

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/swagger-resources/**",
            "/webjars/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/docs",
            "/docs/**",
            "/api/v1/login",
            "/api/v1/refresh-token",
            "/api/v1/register",
            "/v3/api-docs/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(spec -> spec.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handler -> new RestUnauthorizedHandler())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                ).addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private JwtAuthenticationFilter jwtFilter() {
        return new JwtAuthenticationFilter(jwtManageService, userQueryService, currentSessionCache, AUTH_WHITELIST);
    }

}
