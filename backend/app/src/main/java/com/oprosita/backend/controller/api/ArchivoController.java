package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.ArchivosApi;
import com.oprosita.backend.dto.ArchivoDto;
import com.oprosita.backend.model.generated.Archivo;
import com.oprosita.backend.service.ArchivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
        Resource archivo = archivoService.descargarArchivo(id.longValue());
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + archivo.getFilename() + "\"")
                .body(archivo);
    }
}
