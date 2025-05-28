package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.UsuariosApi;
import com.oprosita.backend.mapper.UsuarioMapper;
import com.oprosita.backend.model.generated.CrearUsuarioRequest;
import com.oprosita.backend.model.generated.Usuario;
import com.oprosita.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UsuarioController implements UsuariosApi {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @Override
    public ResponseEntity<List<Usuario>> obtenerUsuarios(String tipo) {
        List<Object> usuarios = usuarioService.obtenerUsuarios(tipo);
        List<Usuario> response = usuarioMapper.mapToUsuarioList(usuarios);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CrearUsuarioRequest> crearUsuario(CrearUsuarioRequest usuario) {
        Object dto = usuarioMapper.mapToDto(usuario);
        Object creado = usuarioService.crearUsuario(dto);
        CrearUsuarioRequest response = usuarioMapper.mapToGenerated(creado);
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<CrearUsuarioRequest> actualizarUsuario(Integer id, CrearUsuarioRequest usuario) {
        Object dto = usuarioMapper.mapToDto(usuario);
        Object actualizado = usuarioService.actualizarUsuario(id.longValue(), dto);
        return ResponseEntity.ok(usuario);
    }

    @Override
    public ResponseEntity<Void> eliminarUsuario(Integer id) {
        usuarioService.eliminarUsuario(id.longValue());
        return ResponseEntity.noContent().build();
    }
}
