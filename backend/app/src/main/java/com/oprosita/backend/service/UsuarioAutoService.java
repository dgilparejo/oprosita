package com.oprosita.backend.service;

import com.oprosita.backend.model.Usuario;

public interface UsuarioAutoService {
    Usuario ensureUsuarioExists(String idKeycloak, String nombre, String rol);
}
