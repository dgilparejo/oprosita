package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.UsuariosApi;
import com.oprosita.backend.mapper.UsuarioMapper;
import com.oprosita.backend.model.generated.CrearUsuario201Response;
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
    public ResponseEntity<CrearUsuario201Response> crearUsuario(CrearUsuarioRequest usuario) {
        Object dto = usuarioMapper.mapToDto(usuario);
        Object creado = usuarioService.crearUsuario(dto);
        CrearUsuario201Response response = usuarioMapper.mapToCrearUsuario201Response(creado);
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<CrearUsuario201Response> actualizarUsuario(Integer id, CrearUsuario201Response request) {
        Object dto = usuarioMapper.mapToDto(request);
        Object actualizado = usuarioService.actualizarUsuario(id.longValue(), dto);
        CrearUsuario201Response response = usuarioMapper.mapToCrearUsuario201Response(actualizado);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> eliminarUsuario(Integer id) {
        usuarioService.eliminarUsuario(id.longValue());
        return ResponseEntity.noContent().build();
    }
}
