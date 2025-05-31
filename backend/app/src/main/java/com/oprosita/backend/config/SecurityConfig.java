package com.oprosita.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, SyncUsuarioFilter syncUsuarioFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(syncUsuarioFilter, org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/noticias").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/archivos/{id}/download").permitAll()
                        .requestMatchers("/api/grupos/**", "/api/novedades/profesor/**", "/api/simulacros/**").hasRole("profesor")
                        .requestMatchers("/api/novedades/alumno/**", "/api/alumnos/**").hasRole("alumno")
                        .requestMatchers("/api/usuarios/**", "/api/contenido/**", "/api/chat/**", "/api/reuniones/**").authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new RealmRoleConverter());
        return converter;
    }
}

