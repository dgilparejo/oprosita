package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.dto.ProfesorDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.GrupoMapper;
import com.oprosita.backend.mapper.ProfesorMapper;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.model.Profesor;
import com.oprosita.backend.repository.GrupoRepository;
import com.oprosita.backend.repository.ProfesorRepository;
import com.oprosita.backend.service.ProfesorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorRepository profesorRepository;
    private final GrupoRepository grupoRepository;
    private final ProfesorMapper profesorMapper;
    private final GrupoMapper grupoMapper;

    @Override
    public ProfesorDto obtenerPorId(Long id) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profesor no encontrado"));
        return profesorMapper.toProfesorDto(profesor);
    }

    @Override
    public List<ProfesorDto> obtenerTodos() {
        return profesorMapper.toProfesorDtoList(profesorRepository.findAll());
    }

    @Override
    public ProfesorDto crear(ProfesorDto dto) {
        Profesor profesor = profesorMapper.toProfesorEntity(dto);
        return profesorMapper.toProfesorDto(profesorRepository.save(profesor));
    }

    @Override
    public ProfesorDto actualizar(Long id, ProfesorDto dto) {
        if (!profesorRepository.existsById(id)) {
            throw new NotFoundException("Profesor no encontrado");
        }
        Profesor profesor = profesorMapper.toProfesorEntity(dto);
        profesor.setId(id);
        return profesorMapper.toProfesorDto(profesorRepository.save(profesor));
    }

    @Override
    public void eliminar(Long id) {
        if (!profesorRepository.existsById(id)) {
            throw new NotFoundException("Profesor no encontrado");
        }
        profesorRepository.deleteById(id);
    }

    @Override
    public List<GrupoDto> obtenerGruposPorProfesor(Long profesorId) {
        Profesor profesor = profesorRepository.findById(profesorId)
                .orElseThrow(() -> new NotFoundException("Profesor no encontrado"));
        return grupoMapper.toGrupoDtoList(profesor.getGrupos());
    }

    @Override
    public ProfesorDto asignarGrupoAProfesor(Long profesorId, Long grupoId) {
        Profesor profesor = profesorRepository.findById(profesorId)
                .orElseThrow(() -> new NotFoundException("Profesor no encontrado"));
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        if (profesor.getGrupos() == null) {
            profesor.setGrupos(new ArrayList<>());
        }
        profesor.getGrupos().add(grupo);
        return profesorMapper.toProfesorDto(profesorRepository.save(profesor));
    }

    @Override
    public void desasignarGrupoDeProfesor(Long profesorId, Long grupoId) {
        Profesor profesor = profesorRepository.findById(profesorId)
                .orElseThrow(() -> new NotFoundException("Profesor no encontrado"));

        if (profesor.getGrupos() != null) {
            profesor.getGrupos().removeIf(g -> g.getId().equals(grupoId));
            profesorRepository.save(profesor);
        }
    }

    @Override
    public ProfesorDto obtenerProfesorPorGrupo(Long grupoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        if (grupo.getProfesor() == null) {
            throw new NotFoundException("El grupo no tiene un profesor asignado");
        }
        return profesorMapper.toProfesorDto(grupo.getProfesor());
    }
}
