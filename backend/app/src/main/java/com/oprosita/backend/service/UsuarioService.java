package com.oprosita.backend.service;

import java.util.List;

public interface UsuarioService {

    List<Object> obtenerUsuarios(String tipo);

    Object crearUsuario(Object usuarioDto);

    Object actualizarUsuario(Long id, Object usuarioDto);

    void eliminarUsuario(Long id);
}
