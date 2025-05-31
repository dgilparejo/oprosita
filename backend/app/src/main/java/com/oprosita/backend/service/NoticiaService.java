package com.oprosita.backend.service;

import com.oprosita.backend.dto.NoticiaDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NoticiaService {
    // Métodos CRUD
    NoticiaDto obtenerPorId(Long id);
    List<NoticiaDto> obtenerTodos();
    NoticiaDto crear(NoticiaDto dto);
    NoticiaDto actualizar(Long id, NoticiaDto dto);
    void eliminar(Long id);

    // Métodos del OpenAPI
    NoticiaDto crearNoticia(String descripcion, MultipartFile file);
}