package com.oprosita.backend.service;

import com.oprosita.backend.dto.ContenidoItemDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContenidoItemService {
    // Métodos CRUD
    ContenidoItemDto obtenerPorId(Long id);
    List<ContenidoItemDto> obtenerTodos();
    ContenidoItemDto crear(ContenidoItemDto dto);
    ContenidoItemDto actualizar(Long id, ContenidoItemDto dto);
    void eliminar(Long id);

    // Métodos del OpenAPI
    List<ContenidoItemDto> obtenerPorAlumno(Long alumnoId);
    ContenidoItemDto crearParaAlumno(Long alumnoId, String texto, String tipoContenido, String mes, MultipartFile file);
    List<ContenidoItemDto> obtenerPorGrupoYMes(Long grupoId, String mes);
    ContenidoItemDto crearParaGrupoPorMes(Long grupoId, Long mesId, ContenidoItemDto contenidoDto);
}