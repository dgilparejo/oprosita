package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.ContenidoApi;
import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.mapper.ContenidoItemMapper;
import com.oprosita.backend.model.TipoContenido;
import com.oprosita.backend.model.generated.ContenidoItem;
import com.oprosita.backend.service.ContenidoItemService;
import com.oprosita.backend.service.UsuarioService;
import com.oprosita.backend.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContenidoItemController implements ContenidoApi {

    private final ContenidoItemService contenidoItemService;
    private final ContenidoItemMapper mapper;
    private final UsuarioService usuarioService;

    @Override
    public ResponseEntity<Void> deleteContenido(Integer id) {
        // Obtener el ID de Keycloak
        String keycloakId = AuthUtils.getUsuarioId();
        if (keycloakId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Obtener el usuario desde la base de datos usando el ID de Keycloak
        com.oprosita.backend.model.Usuario usuario = usuarioService.getUsuarioByKeycloakId(keycloakId);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Llamar al servicio pasando el ID del usuario autenticado
        contenidoItemService.eliminar(id.longValue(), usuario.getId());

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ContenidoItem> addContenidoPrivadoAlumno(
            Integer alumnoId, String texto, String tipoContenido, MultipartFile file) {

        ContenidoItemDto dto = contenidoItemService.crearParaAlumno(
                alumnoId.longValue(), texto, tipoContenido, file);
        return ResponseEntity.status(201).body(mapper.toGeneratedContenidoItem(dto));
    }

    @Override
    public ResponseEntity<ContenidoItem> addContenidoToGrupoMes(
            Integer grupoId, Integer mesId, String texto, String tipoContenido, Integer autorId, MultipartFile file) {

        ContenidoItemDto nuevoContenido = ContenidoItemDto.builder()
                .texto(texto)
                .tipoContenido(TipoContenido.valueOf(tipoContenido.toUpperCase()))
                .autorId((int) autorId.longValue())
                .build();

        ContenidoItemDto dto = contenidoItemService.crearParaGrupoPorMes(
                grupoId.longValue(), mesId.longValue(), nuevoContenido,file);

        return ResponseEntity.status(201).body(mapper.toGeneratedContenidoItem(dto));
    }

    @Override
    public ResponseEntity<List<ContenidoItem>> getContenidoGrupoMes(Integer grupoId, Integer mesId) {
        List<ContenidoItemDto> dtos = contenidoItemService.obtenerPorGrupoYMes(
                grupoId.longValue(), String.valueOf(mesId));
        return ResponseEntity.ok(dtos.stream()
                .map(mapper::toGeneratedContenidoItem)
                .toList());
    }

    @Override
    public ResponseEntity<List<ContenidoItem>> getContenidoPrivadoAlumno(Integer alumnoId) {
        List<ContenidoItemDto> dtos = contenidoItemService.obtenerPorAlumno(alumnoId.longValue());
        return ResponseEntity.ok(dtos.stream()
                .map(mapper::toGeneratedContenidoItem)
                .toList());
    }
}

