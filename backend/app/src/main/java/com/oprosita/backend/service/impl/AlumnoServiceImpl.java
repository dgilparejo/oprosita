package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.AlumnoMapper;
import com.oprosita.backend.mapper.GrupoMapper;
import com.oprosita.backend.model.Alumno;
import com.oprosita.backend.repository.*;
import com.oprosita.backend.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final AlumnoMapper alumnoMapper;
    private final GrupoMapper grupoMapper;

    @Override
    public AlumnoDto obtenerPorId(Long id) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));
        return alumnoMapper.toAlumnoDto(alumno);
    }

    @Override
    public List<AlumnoDto> obtenerTodos() {
        return alumnoRepository.findAll().stream()
                .map(alumnoMapper::toAlumnoDto)
                .collect(Collectors.toList());
    }

    @Override
    public AlumnoDto crear(AlumnoDto dto) {
        Alumno alumno = alumnoMapper.toAlumnoEntity(dto);
        return alumnoMapper.toAlumnoDto(alumnoRepository.save(alumno));
    }

    @Override
    public AlumnoDto actualizar(Long id, AlumnoDto dto) {
        if (!alumnoRepository.existsById(id)) {
            throw new NotFoundException("Alumno no encontrado");
        }
        Alumno alumno = alumnoMapper.toAlumnoEntity(dto);
        alumno.setId(id);
        return alumnoMapper.toAlumnoDto(alumnoRepository.save(alumno));
    }

    @Override
    public void eliminar(Long id) {
        if (!alumnoRepository.existsById(id)) {
            throw new NotFoundException("Alumno no encontrado");
        }
        alumnoRepository.deleteById(id);
    }

    @Override
    public List<GrupoDto> obtenerGrupoPorAlumno(Long alumnoId) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));

        if (alumno.getGrupo() == null) {
            return List.of();
        }

        return List.of(grupoMapper.toGrupoDto(alumno.getGrupo()));
    }
}
