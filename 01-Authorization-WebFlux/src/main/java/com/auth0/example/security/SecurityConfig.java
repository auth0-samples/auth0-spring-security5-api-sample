package com.auth0.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configures our application with Spring Security to restrict access to our API endpoints.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        /*
        This is where we configure the security required for our endpoints and setup our app to serve as
        an OAuth2 Resource Server, using JWT validation.
        */
        return http
                .authorizeExchange((auth) -> { auth
                    .pathMatchers("/api/public").permitAll()
                    .pathMatchers("/api/private").authenticated()
                    .pathMatchers("/api/private-scoped").hasAuthority("SCOPE_read:messages");
                })
                .cors(withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(withDefaults())
                )
                .build();
    }
}
