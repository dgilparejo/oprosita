package com.oprosita.backend.service;

import com.oprosita.backend.dto.ArchivoDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArchivoService {
    // Métodos CRUD
    ArchivoDto obtenerPorId(Long id);
    List<ArchivoDto> obtenerTodos();
    ArchivoDto crear(ArchivoDto dto);
    ArchivoDto actualizar(Long id, ArchivoDto dto);
    void eliminar(Long id);

    // Métodos del OpenAPI
    ArchivoDto subirArchivo(MultipartFile file);
    Resource descargarArchivo(Long id);
}