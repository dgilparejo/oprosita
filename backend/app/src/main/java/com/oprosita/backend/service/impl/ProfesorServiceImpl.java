
package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.dto.ProfesorDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.GeneralMapper;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.model.Profesor;
import com.oprosita.backend.repository.GrupoRepository;
import com.oprosita.backend.repository.ProfesorRepository;
import com.oprosita.backend.service.ProfesorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorRepository profesorRepository;
    private final GrupoRepository grupoRepository;
    private final GeneralMapper mapper;

    public ProfesorServiceImpl(ProfesorRepository profesorRepository, GrupoRepository grupoRepository, GeneralMapper mapper) {
        this.profesorRepository = profesorRepository;
        this.grupoRepository = grupoRepository;
        this.mapper = mapper;
    }

    @Override
    public ProfesorDto obtenerPorId(Long id) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profesor no encontrado"));
        return mapper.toDto(profesor);
    }

    @Override
    public List<ProfesorDto> obtenerTodos() {
        return mapper.toDtoList(profesorRepository.findAll());
    }

    @Override
    public ProfesorDto crear(ProfesorDto dto) {
        Profesor profesor = mapper.toEntity(dto);
        return mapper.toDto(profesorRepository.save(profesor));
    }

    @Override
    public ProfesorDto actualizar(Long id, ProfesorDto dto) {
        if (!profesorRepository.existsById(id)) {
            throw new NotFoundException("Profesor no encontrado");
        }
        Profesor profesor = mapper.toEntity(dto);
        profesor.setId(id);
        return mapper.toDto(profesorRepository.save(profesor));
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
        return List.of(mapper.toDto(profesor.getGrupo()));
    }

    @Override
    public ProfesorDto asignarGrupoAProfesor(Long profesorId, Long grupoId) {
        Profesor profesor = profesorRepository.findById(profesorId)
                .orElseThrow(() -> new NotFoundException("Profesor no encontrado"));
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));

        profesor.setGrupo(grupo);
        return mapper.toDto(profesorRepository.save(profesor));
    }

    @Override
    public void desasignarGrupoDeProfesor(Long profesorId, Long grupoId) {
        Profesor profesor = profesorRepository.findById(profesorId)
                .orElseThrow(() -> new NotFoundException("Profesor no encontrado"));
        if (profesor.getGrupo() != null && profesor.getGrupo().getId().equals(grupoId)) {
            profesor.setGrupo(null);
            profesorRepository.save(profesor);
        }
    }
}
