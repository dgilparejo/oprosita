package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.GeneralMapper;
import com.oprosita.backend.model.Alumno;
import com.oprosita.backend.model.ContenidoItem;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.repository.AlumnoRepository;
import com.oprosita.backend.repository.ContenidoItemRepository;
import com.oprosita.backend.repository.GrupoRepository;
import com.oprosita.backend.service.GrupoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository grupoRepository;
    private final AlumnoRepository alumnoRepository;
    private final ContenidoItemRepository contenidoItemRepository;
    private final GeneralMapper mapper;

    public GrupoServiceImpl(GrupoRepository grupoRepository,
                            AlumnoRepository alumnoRepository,
                            ContenidoItemRepository contenidoItemRepository,
                            GeneralMapper mapper) {
        this.grupoRepository = grupoRepository;
        this.alumnoRepository = alumnoRepository;
        this.contenidoItemRepository = contenidoItemRepository;
        this.mapper = mapper;
    }

    @Override
    public GrupoDto obtenerPorId(Long id) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        return mapper.toDto(grupo);
    }

    @Override
    public List<GrupoDto> obtenerTodos() {
        return grupoRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public GrupoDto crear(GrupoDto dto) {
        Grupo grupo = mapper.toEntity(dto);
        grupo = grupoRepository.save(grupo);
        return mapper.toDto(grupo);
    }

    @Override
    public GrupoDto actualizar(Long id, GrupoDto dto) {
        if (!grupoRepository.existsById(id)) {
            throw new NotFoundException("Grupo no encontrado");
        }
        Grupo grupo = mapper.toEntity(dto);
        grupo.setId(id);
        grupo = grupoRepository.save(grupo);
        return mapper.toDto(grupo);
    }

    @Override
    public void eliminar(Long id) {
        if (!grupoRepository.existsById(id)) {
            throw new NotFoundException("Grupo no encontrado");
        }
        grupoRepository.deleteById(id);
    }

    @Override
    public List<AlumnoDto> obtenerAlumnosPorGrupo(Long grupoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        return grupo.getUsuarios().stream()
                .filter(u -> u instanceof Alumno)
                .map(u -> mapper.toDto((Alumno) u))
                .collect(Collectors.toList());
    }

    @Override
    public AlumnoDto agregarAlumnoAGrupo(Long grupoId, AlumnoDto alumnoDto) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        Alumno alumno = mapper.toEntity(alumnoDto);
        alumno.setGrupo(grupo);
        alumno = alumnoRepository.save(alumno);
        return mapper.toDto(alumno);
    }

    @Override
    public void eliminarAlumnoDeGrupo(Long grupoId, Long alumnoId) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));
        if (alumno.getGrupo() == null || !alumno.getGrupo().getId().equals(grupoId)) {
            throw new NotFoundException("El alumno no pertenece al grupo indicado");
        }
        alumnoRepository.deleteById(alumnoId);
    }

    @Override
    public List<ContenidoItemDto> obtenerContenidoPorGrupoYMes(Long grupoId, String mes) {
        return contenidoItemRepository.findAll().stream()
                .filter(c -> c.getGrupoId().equals(grupoId) && c.getMes().equalsIgnoreCase(mes))
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContenidoItemDto agregarContenidoAGrupoPorMes(Long grupoId, String mes, ContenidoItemDto contenidoDto) {
        ContenidoItem contenido = mapper.toEntity(contenidoDto);
        contenido.setGrupoId(grupoId);
        contenido.setMes(mes);
        contenido = contenidoItemRepository.save(contenido);
        return mapper.toDto(contenido);
    }
}
