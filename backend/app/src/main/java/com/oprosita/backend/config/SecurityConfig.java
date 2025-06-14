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
                        // Todos pueden acceder a estas rutas
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Usuarios
                        .requestMatchers(HttpMethod.GET, "/usuarios/me").authenticated()
                        .requestMatchers(HttpMethod.GET, "/usuarios").hasAnyRole("profesor", "alumno")
                        .requestMatchers(HttpMethod.GET, "/usuarios/**").hasRole("profesor")
                        .requestMatchers(HttpMethod.POST, "/usuarios/**").hasRole("profesor")
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("profesor")

                        // Alumnado
                        .requestMatchers(HttpMethod.GET,"/alumnos/**").authenticated()
                        .requestMatchers(HttpMethod.POST,"/alumnos/**").hasRole("alumno")
                        .requestMatchers(HttpMethod.DELETE,"/alumnos/**").hasRole("profesor")

                        // Contenido
                        .requestMatchers("/contenido/**").authenticated()

                        // Grupos
                        .requestMatchers(HttpMethod.GET,"/grupos/**").authenticated()
                        .requestMatchers(HttpMethod.POST,"/grupos/**").hasRole("profesor")
                        .requestMatchers(HttpMethod.DELETE,"/grupos/**").hasRole("profesor")

                        // Novedades
                        .requestMatchers(HttpMethod.GET, "/novedades/profesor").hasRole("profesor")
                        .requestMatchers(HttpMethod.POST, "/novedades/profesor").hasRole("alumno")
                        .requestMatchers(HttpMethod.DELETE, "/novedades/**").hasRole("profesor")
                        .requestMatchers(HttpMethod.GET, "/novedades/alumno").authenticated()
                        .requestMatchers(HttpMethod.POST, "/novedades/alumno").hasRole("profesor")

                        // Reuniones
                        .requestMatchers(HttpMethod.PUT, "/reuniones/**").hasRole("profesor")
                        .requestMatchers(HttpMethod.DELETE, "/reuniones/**").hasRole("profesor")

                        // Simulacros
                        .requestMatchers(HttpMethod.GET, "/simulacros").authenticated()
                        .requestMatchers(HttpMethod.POST, "/simulacros").hasRole("profesor")
                        .requestMatchers(HttpMethod.DELETE, "/simulacros/**").hasRole("profesor")

                        // Noticias
                        .requestMatchers(HttpMethod.GET, "/noticias").authenticated()
                        .requestMatchers(HttpMethod.POST, "/noticias").hasRole("profesor")
                        .requestMatchers(HttpMethod.DELETE, "/noticias/**").hasRole("profesor")

                        // Archivos
                        .requestMatchers("/archivos/**").authenticated()

                        // Chat
                        .requestMatchers( "/chat/**").authenticated()

                        // Resto de rutas
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
