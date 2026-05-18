package com.test.gitradar.config;
import com.test.gitradar.services.CustomOAuth2UserService;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth.
                requestMatchers("/api/v1/public/**", "/v1/public/**", "/error"). // Endpoints which are allowed to be visited without auth
                permitAll(). // We give permission for all users to visit requestMatchers
                anyRequest(). // For any another endpoint...
                authenticated() // User have to be authorized
        ).
                oauth2Login(oauth2 -> oauth2. // Here we use OAuth2 protocol to log in
                userInfoEndpoint(userInfo -> userInfo. // After logging in, we connect our Service to make smth
                        // In this current case we save some important user data to the PostgreSQL
                        userService(customOAuth2UserService)). // Here is our Service
                defaultSuccessUrl("/api/v1/user/profile", true) // What is the endpoint for a user after successfully logging in
        ).logout(logout -> logout.
                logoutSuccessUrl("/v1/public/"). // Where do we redirect our user after logging out
                permitAll()); // We allow all users to come to this endpoint
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(java.util.List.of("http://localhost", "http://localhost:80"));
        configuration.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(java.util.List.of("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
