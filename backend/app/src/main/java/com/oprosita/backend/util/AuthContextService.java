package com.oprosita.backend.util;

import com.oprosita.backend.model.Usuario;
import com.oprosita.backend.service.UsuarioAutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthContextService {

    private final UsuarioAutoService usuarioAutoService;

    public Usuario syncUsuarioConKeycloak() {
        var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String idKeycloak = jwt.getSubject(); // sub
        String nombre = (String) jwt.getClaims().get("name");

        var realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
        var roles = (List<String>) realmAccess.get("roles");
        String rol = roles.contains("profesor") ? "profesor" : "alumno";

        return usuarioAutoService.ensureUsuarioExists(idKeycloak, nombre, rol);
    }
}
