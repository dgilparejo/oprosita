package com.oprosita.backend.service.impl;

import com.oprosita.backend.model.Alumno;
import com.oprosita.backend.model.Profesor;
import com.oprosita.backend.model.Usuario;
import com.oprosita.backend.repository.UsuarioRepository;
import com.oprosita.backend.service.UsuarioAutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioAutoServiceImpl implements UsuarioAutoService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public Usuario ensureUsuarioExists(String idKeycloak, String nombre, String rol) {
        return usuarioRepository.findByIdKeycloak(idKeycloak)
                .orElseGet(() -> {
                    Usuario nuevo;
                    if ("profesor".equals(rol)) {
                        nuevo = new Profesor();
                    } else {
                        nuevo = new Alumno();
                    }
                    nuevo.setIdKeycloak(idKeycloak);
                    nuevo.setNombre(nombre);
                    return usuarioRepository.save(nuevo);
                });
    }
}

