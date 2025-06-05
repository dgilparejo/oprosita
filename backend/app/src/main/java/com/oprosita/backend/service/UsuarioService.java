package com.oprosita.backend.service;

import com.oprosita.backend.model.Usuario;

import java.util.List;

public interface UsuarioService {

    List<Object> obtenerUsuarios(String tipo);

    Object crearUsuario(Object usuarioDto);

    void eliminarUsuario(Long id);

    Usuario getUsuarioByKeycloakId(String idKeycloak);

}
