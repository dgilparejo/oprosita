package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.ProfesorDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.AlumnoMapper;
import com.oprosita.backend.mapper.ProfesorMapper;
import com.oprosita.backend.model.Alumno;
import com.oprosita.backend.model.Profesor;
import com.oprosita.backend.repository.AlumnoRepository;
import com.oprosita.backend.repository.ProfesorRepository;
import com.oprosita.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final AlumnoRepository alumnoRepository;
    private final ProfesorRepository profesorRepository;
    private final AlumnoMapper alumnoMapper;
    private final ProfesorMapper profesorMapper;

    @Override
    public List<Object> obtenerUsuarios(String tipo) {
        List<Object> resultado = new ArrayList<>();
        if (tipo == null) {
            resultado.addAll(alumnoMapper.toAlumnoDtoList(alumnoRepository.findAll()));
            resultado.addAll(profesorMapper.toProfesorDtoList(profesorRepository.findAll()));
        } else if ("alumno".equalsIgnoreCase(tipo)) {
            resultado.addAll(alumnoMapper.toAlumnoDtoList(alumnoRepository.findAll()));
        } else if ("profesor".equalsIgnoreCase(tipo)) {
            resultado.addAll(profesorMapper.toProfesorDtoList(profesorRepository.findAll()));
        }
        return resultado;
    }

    @Override
    public Object crearUsuario(Object usuarioDto) {
        if (usuarioDto instanceof AlumnoDto alumnoDto) {
            Alumno alumno = alumnoMapper.toAlumnoEntity(alumnoDto);
            return alumnoMapper.toAlumnoDto(alumnoRepository.save(alumno));
        } else if (usuarioDto instanceof ProfesorDto profesorDto) {
            Profesor profesor = profesorMapper.toProfesorEntity(profesorDto);
            return profesorMapper.toProfesorDto(profesorRepository.save(profesor));
        }
        throw new IllegalArgumentException("Tipo de usuario no soportado");
    }

    @Override
    public Object actualizarUsuario(Long id, Object usuarioDto) {
        if (usuarioDto instanceof AlumnoDto alumnoDto) {
            Alumno alumno = alumnoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));
            alumno.setNombre(alumnoDto.getNombre());
            alumno.setGrupo(alumnoDto.getGrupoId() != null ?
                    alumno.getGrupo() : null); // Puedes mejorar esto según lógica completa
            return alumnoMapper.toAlumnoDto(alumnoRepository.save(alumno));
        } else if (usuarioDto instanceof ProfesorDto profesorDto) {
            Profesor profesor = profesorRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Profesor no encontrado"));
            profesor.setNombre(profesorDto.getNombre());
            return profesorMapper.toProfesorDto(profesorRepository.save(profesor));
        }
        throw new IllegalArgumentException("Tipo de usuario no soportado");
    }

    @Override
    public void eliminarUsuario(Long id) {
        if (alumnoRepository.existsById(id)) {
            alumnoRepository.deleteById(id);
        } else if (profesorRepository.existsById(id)) {
            profesorRepository.deleteById(id);
        } else {
            throw new NotFoundException("Usuario no encontrado");
        }
    }
}
