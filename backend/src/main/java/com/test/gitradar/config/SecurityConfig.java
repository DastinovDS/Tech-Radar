package com.test.gitradar.config;
import com.test.gitradar.services.CustomOAuth2UserService;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.
                requestMatchers("/v1/public/**", "/error"). // Endpoints which are allowed to be visited without auth
                permitAll(). // We give permission for all users to visit requestMatchers
                anyRequest(). // For any another endpoint...
                authenticated() // User have to be authorized
        ).
                oauth2Login(oauth2 -> oauth2. // Here we use OAuth2 protocol to log in
                userInfoEndpoint(userInfo -> userInfo. // After logging in, we connect our Service to make smth
                        // In this current case we save some important user data to the PostgreSQL
                        userService(customOAuth2UserService)). // Here is our Service
                defaultSuccessUrl("/user", true) // What is the endpoint for a user after successfully logging in
        ).logout(logout -> logout.
                logoutSuccessUrl("/v1/public/"). // Where do we redirect our user after logging out
                permitAll()); // We allow all users to come to this endpoint
        return http.build();
    }
}
