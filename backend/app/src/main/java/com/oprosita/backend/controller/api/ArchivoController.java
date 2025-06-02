package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.ArchivosApi;
import com.oprosita.backend.dto.ArchivoDto;
import com.oprosita.backend.model.generated.Archivo;
import com.oprosita.backend.service.ArchivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

@RestController
@RequiredArgsConstructor
public class ArchivoController implements ArchivosApi {

    private final ArchivoService archivoService;

    @Override
    public ResponseEntity<Archivo> uploadArchivo(MultipartFile file) {
        ArchivoDto dto = archivoService.subirArchivo(file);

        Archivo archivo = new Archivo()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .tipo(dto.getTipo())
                .url(dto.getUrl())
                .datos(dto.getDatos());

        return ResponseEntity.status(201).body(archivo);
    }
    @Override
    public ResponseEntity<Resource> downloadArchivo(Integer id) {
        com.oprosita.backend.model.Archivo archivo = archivoService.obtenerEntidadPorId(id.longValue());
        ByteArrayResource resource = new ByteArrayResource(archivo.getDatos());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + archivo.getNombre() + "\"")
                .contentType(MediaType.parseMediaType(archivo.getTipo()))
                .contentLength(archivo.getDatos().length)
                .body(resource);
    }

    @Override
    public ResponseEntity<Archivo> getArchivoInfo(Integer id) {
        ArchivoDto dto = archivoService.obtenerPorId(id.longValue());
        Archivo archivo = new Archivo()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .tipo(dto.getTipo())
                .url(dto.getUrl());
        return ResponseEntity.ok(archivo);
    }
}
