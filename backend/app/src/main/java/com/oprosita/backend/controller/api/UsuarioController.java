package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.UsuariosApi;
import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.ProfesorDto;
import com.oprosita.backend.mapper.AlumnoMapper;
import com.oprosita.backend.mapper.ProfesorMapper;
import com.oprosita.backend.mapper.UsuarioMapper;
import com.oprosita.backend.model.generated.*;
import com.oprosita.backend.service.UsuarioService;
import com.oprosita.backend.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UsuarioController implements UsuariosApi {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final AlumnoMapper alumnoMapper;
    private final ProfesorMapper profesorMapper;

    @Override
    public ResponseEntity<List<Usuario>> obtenerUsuarios(String tipo) {
        List<Object> usuarios = usuarioService.obtenerUsuarios(tipo);
        List<Usuario> response = usuarioMapper.mapToUsuarioList(usuarios);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CrearUsuario201Response> crearUsuario(CrearUsuarioRequest usuario) {
        Object dto = usuarioMapper.mapToDto(usuario);
        Object creado = usuarioService.crearUsuario(dto);
        CrearUsuario201Response response = usuarioMapper.mapToCrearUsuario201Response(creado);
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<Void> eliminarUsuario(Integer id) {
        usuarioService.eliminarUsuario(id.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CrearUsuario201Response> getMiUsuario() {
        String keycloakId = AuthUtils.getUsuarioId();
        if (keycloakId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        com.oprosita.backend.model.Usuario usuario = usuarioService.getUsuarioByKeycloakId(keycloakId);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        CrearUsuario201Response response = usuarioMapper.fromUsuario(usuario);
        return ResponseEntity.ok(response);
    }
}
