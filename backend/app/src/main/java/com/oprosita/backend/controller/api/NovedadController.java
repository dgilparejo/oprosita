package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.NovedadesApi;
import com.oprosita.backend.dto.NovedadDto;
import com.oprosita.backend.mapper.NovedadMapper;
import com.oprosita.backend.model.generated.Novedad;
import com.oprosita.backend.service.NovedadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class NovedadController implements NovedadesApi {

    private final NovedadService novedadService;
    private final NovedadMapper mapper;

    @Override
    public ResponseEntity<Void> deleteNovedad(Integer id) {
        novedadService.eliminar(id.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteNovedadesAlumno() {
        novedadService.eliminarNovedadesAlumno();
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Novedad>> getNovedadesAlumno() {
        List<NovedadDto> dtos = novedadService.obtenerNovedadesAlumno();
        List<Novedad> novedades = dtos.stream()
                .map(mapper::toNovedadGenerated)
                .collect(Collectors.toList());
        return ResponseEntity.ok(novedades);
    }

    @Override
    public ResponseEntity<List<Novedad>> getNovedadesProfesor(String tipo, OffsetDateTime fechaDesde) {
        List<NovedadDto> dtos;

        if (tipo != null && fechaDesde != null) {
            dtos = novedadService.obtenerNovedadesProfesorPorTipo(tipo, fechaDesde);
        } else {
            dtos = novedadService.obtenerNovedadesProfesor();
        }

        List<Novedad> novedades = dtos.stream()
                .map(mapper::toNovedadGenerated)
                .collect(Collectors.toList());

        return ResponseEntity.ok(novedades);
    }

    @Override
    public ResponseEntity<Novedad> createNovedadProfesor(Novedad body) {
        NovedadDto dto = mapper.toNovedadDto(body);
        NovedadDto creado = novedadService.crearNovedadProfesor(dto);
        return ResponseEntity.status(201).body(mapper.toNovedadGenerated(creado));
    }

    @Override
    public ResponseEntity<Novedad> createNovedadAlumno(Novedad body) {
        NovedadDto dto = mapper.toNovedadDto(body);
        NovedadDto creado = novedadService.crearNovedadAlumno(dto);
        return ResponseEntity.status(201).body(mapper.toNovedadGenerated(creado));
    }

    @Override
    public ResponseEntity<Novedad> updateNovedadAlumno(Novedad body) {
        NovedadDto dto = mapper.toNovedadDto(body);
        NovedadDto actualizado = novedadService.actualizarNovedadAlumno(dto);
        return ResponseEntity.ok(mapper.toNovedadGenerated(actualizado));
    }
}
