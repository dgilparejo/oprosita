package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.AlumnosApi;
import com.oprosita.backend.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class AlumnoController implements AlumnosApi {

    private final AlumnoService alumnoService;

    @Override
    public ResponseEntity<Void> deleteAlumno(Integer id) {
        alumnoService.eliminar(id.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteContenidoFromAlumno(Integer alumnoId, Integer contenidoId) {
        alumnoService.eliminarContenidoDeAlumno(alumnoId.longValue(), contenidoId.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> addContenidoToAlumno(
            Integer alumnoId,
            String texto,
            String tipoContenido,
            String mes,
            MultipartFile file) {
        alumnoService.agregarContenidoAAlumno(alumnoId.longValue(), texto, tipoContenido, mes, file);
        return ResponseEntity.status(201).build();
    }
}
