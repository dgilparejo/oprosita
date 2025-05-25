package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.dto.MesDto;
import com.oprosita.backend.exception.AlreadyExistsException;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.AlumnoMapper;
import com.oprosita.backend.mapper.ContenidoItemMapper;
import com.oprosita.backend.mapper.GrupoMapper;
import com.oprosita.backend.model.Alumno;
import com.oprosita.backend.model.ContenidoItem;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.repository.AlumnoRepository;
import com.oprosita.backend.repository.ContenidoItemRepository;
import com.oprosita.backend.repository.GrupoRepository;
import com.oprosita.backend.service.GrupoService;
import com.oprosita.backend.service.MesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository grupoRepository;
    private final AlumnoRepository alumnoRepository;
    private final ContenidoItemRepository contenidoItemRepository;
    private final MesService mesService;
    private final GrupoMapper grupoMapper;
    private final AlumnoMapper alumnoMapper;
    private final ContenidoItemMapper contenidoItemMapper;

    @Override
    public GrupoDto obtenerPorId(Long id) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        return grupoMapper.toGrupoDto(grupo);
    }

    @Override
    public List<GrupoDto> obtenerTodos() {
        return grupoRepository.findAll().stream()
                .map(grupoMapper::toGrupoDto)
                .collect(Collectors.toList());
    }

    @Override
    public GrupoDto crear(GrupoDto dto) {
        // Si alguien manda un ID en el Grupo, lo anulamos o lanzamos excepción
        if (dto.getId() != null) {
            throw new IllegalArgumentException("El campo id debe estar vacío al crear un nuevo grupo");
        }

        // Y hacemos lo mismo con los meses
        if (dto.getMeses() != null) {
            dto.getMeses().forEach(m -> {
                if (m.getId() != null) {
                    throw new IllegalArgumentException("Los meses no deben tener ID al ser creados");
                }
            });
        }

        if (grupoRepository.existsByNombre(dto.getNombre())) {
            throw new AlreadyExistsException("Ya existe un grupo con ese nombre");
        }

        Grupo grupo = grupoMapper.toGrupoEntity(dto);
        grupo = grupoRepository.save(grupo);
        return grupoMapper.toGrupoDto(grupo);
    }

    @Override
    public GrupoDto actualizar(Long id, GrupoDto dto) {
        if (!grupoRepository.existsById(id)) {
            throw new NotFoundException("Grupo no encontrado");
        }
        Grupo grupo = grupoMapper.toGrupoEntity(dto);
        grupo.setId(id);
        grupo = grupoRepository.save(grupo);
        return grupoMapper.toGrupoDto(grupo);
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
                .map(u -> alumnoMapper.toAlumnoDto((Alumno) u))
                .collect(Collectors.toList());
    }

    @Override
    public AlumnoDto agregarAlumnoAGrupo(Long grupoId, AlumnoDto alumnoDto) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        Alumno alumno = alumnoMapper.toAlumnoEntity(alumnoDto);
        alumno.setGrupo(grupo);
        alumno = alumnoRepository.save(alumno);
        return alumnoMapper.toAlumnoDto(alumno);
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
                .filter(c -> c.getGrupo() != null &&
                        c.getGrupo().getId().equals(grupoId) &&
                        c.getMes().equalsIgnoreCase(mes))
                .map(contenidoItemMapper::toContenidoItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContenidoItemDto agregarContenidoAGrupoPorMes(Long grupoId, String mes, ContenidoItemDto contenidoDto) {
        ContenidoItem contenido = contenidoItemMapper.toContenidoItemEntity(contenidoDto);

        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        contenido.setGrupo(grupo);
        contenido.setMes(mes);

        contenido = contenidoItemRepository.save(contenido);
        return contenidoItemMapper.toContenidoItemDto(contenido);
    }

    @Override
    public void eliminarContenidoDeGrupoPorMes(Long grupoId, String mes, Long contenidoId) {
        ContenidoItem contenido = contenidoItemRepository.findById(contenidoId)
                .orElseThrow(() -> new NotFoundException("Contenido no encontrado"));

        if (contenido.getGrupo() == null || !contenido.getGrupo().getId().equals(grupoId) || !mes.equalsIgnoreCase(contenido.getMes())) {
            throw new NotFoundException("Contenido no pertenece al grupo o mes indicado");
        }

        contenidoItemRepository.deleteById(contenidoId);
    }

    @Override
    public MesDto agregarMesAGrupo(Long grupoId, MesDto mesDto) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        mesDto.setGrupoId(grupo.getId());
        return mesService.crear(mesDto);
    }

    @Override
    public List<MesDto> obtenerMesesPorGrupo(Long grupoId) {
        return mesService.obtenerMesesPorGrupo(grupoId);
    }
}
