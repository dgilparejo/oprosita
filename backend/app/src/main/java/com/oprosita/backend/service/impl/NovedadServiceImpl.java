package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.NovedadDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.GeneralMapper;
import com.oprosita.backend.model.Novedad;
import com.oprosita.backend.model.TipoDestinatario;
import com.oprosita.backend.repository.NovedadRepository;
import com.oprosita.backend.service.NovedadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NovedadServiceImpl implements NovedadService {

    private final NovedadRepository novedadRepository;
    private final GeneralMapper generalMapper;

    public NovedadServiceImpl(NovedadRepository novedadRepository, GeneralMapper generalMapper) {
        this.novedadRepository = novedadRepository;
        this.generalMapper = generalMapper;
    }

    @Override
    public NovedadDto obtenerPorId(Long id) {
        Novedad novedad = novedadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Novedad no encontrada"));
        return generalMapper.toNovedadDto(novedad);
    }

    @Override
    public List<NovedadDto> obtenerTodos() {
        return novedadRepository.findAll().stream()
                .map(generalMapper::toNovedadDto)
                .collect(Collectors.toList());
    }

    @Override
    public NovedadDto crear(NovedadDto dto) {
        Novedad novedad = generalMapper.toNovedadEntity(dto);
        novedad.setFechaCreacion(OffsetDateTime.now());
        return generalMapper.toNovedadDto(novedadRepository.save(novedad));
    }

    @Override
    public NovedadDto actualizar(Long id, NovedadDto dto) {
        if (!novedadRepository.existsById(id)) {
            throw new NotFoundException("Novedad no encontrada");
        }
        Novedad novedad = generalMapper.toNovedadEntity(dto);
        novedad.setId(id);
        return generalMapper.toNovedadDto(novedadRepository.save(novedad));
    }

    @Override
    public void eliminar(Long id) {
        if (!novedadRepository.existsById(id)) {
            throw new NotFoundException("Novedad no encontrada");
        }
        novedadRepository.deleteById(id);
    }

    @Override
    public List<NovedadDto> obtenerNovedadesProfesor() {
        return novedadRepository.findAll().stream()
                .filter(n -> n.getTipoDestinatario() == TipoDestinatario.PROFESOR)
                .map(generalMapper::toNovedadDto)
                .collect(Collectors.toList());
    }

    @Override
    public NovedadDto crearNovedadProfesor(NovedadDto novedadDto) {
        novedadDto.setTipoDestinatario(TipoDestinatario.PROFESOR);
        novedadDto.setFechaCreacion(OffsetDateTime.now());
        Novedad novedad = generalMapper.toNovedadEntity(novedadDto);
        return generalMapper.toNovedadDto(novedadRepository.save(novedad));
    }

    @Override
    public List<NovedadDto> obtenerNovedadesAlumno() {
        return novedadRepository.findAll().stream()
                .filter(n -> n.getTipoDestinatario() == TipoDestinatario.ALUMNO)
                .map(generalMapper::toNovedadDto)
                .collect(Collectors.toList());
    }

    @Override
    public NovedadDto crearNovedadAlumno(NovedadDto novedadDto) {
        novedadDto.setTipoDestinatario(TipoDestinatario.ALUMNO);
        novedadDto.setFechaCreacion(OffsetDateTime.now());
        Novedad novedad = generalMapper.toNovedadEntity(novedadDto);
        return generalMapper.toNovedadDto(novedadRepository.save(novedad));
    }

    @Override
    public NovedadDto actualizarNovedadAlumno(NovedadDto novedadDto) {
        if (novedadDto.getId() == null || !novedadRepository.existsById(novedadDto.getId().longValue())) {
            throw new NotFoundException("Novedad no encontrada");
        }
        Novedad novedad = generalMapper.toNovedadEntity(novedadDto);
        return generalMapper.toNovedadDto(novedadRepository.save(novedad));
    }

    @Override
    public void eliminarNovedadesAlumno() {
        List<Novedad> novedadesAlumno = novedadRepository.findAll().stream()
                .filter(n -> n.getTipoDestinatario() == TipoDestinatario.ALUMNO)
                .collect(Collectors.toList());
        novedadRepository.deleteAll(novedadesAlumno);
    }

    @Override
    public List<NovedadDto> obtenerNovedadesProfesorPorTipo(String tipo, OffsetDateTime fechaDesde) {
        TipoDestinatario destinatario = TipoDestinatario.valueOf(tipo.toUpperCase());
        return novedadRepository.findAll().stream()
                .filter(n -> n.getTipoDestinatario() == destinatario &&
                        n.getFechaCreacion().isAfter(fechaDesde))
                .map(generalMapper::toNovedadDto)
                .collect(Collectors.toList());
    }

    @Override
    public void registrarNovedadAutomatica(Long alumnoId, String accion, String detalle) {
        String texto = String.format("Alumno ID %d realizó: %s - %s", alumnoId, accion, detalle);
        Novedad novedad = Novedad.builder()
                .texto(texto)
                .tipoDestinatario(TipoDestinatario.ALUMNO)
                .fechaCreacion(OffsetDateTime.now())
                .build();
        novedadRepository.save(novedad);
    }
}
