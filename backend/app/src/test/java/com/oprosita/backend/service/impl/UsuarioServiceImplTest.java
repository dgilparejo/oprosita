package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.ProfesorDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.AlumnoMapper;
import com.oprosita.backend.mapper.ProfesorMapper;
import com.oprosita.backend.model.Alumno;
import com.oprosita.backend.model.Profesor;
import com.oprosita.backend.model.Usuario;
import com.oprosita.backend.repository.AlumnoRepository;
import com.oprosita.backend.repository.ProfesorRepository;
import com.oprosita.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock private AlumnoRepository alumnoRepository;
    @Mock private ProfesorRepository profesorRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private AlumnoMapper alumnoMapper;
    @Mock private ProfesorMapper profesorMapper;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void givenTipoNull_whenObtenerUsuarios_thenReturnAll() {
        // given
        Alumno alumno = new Alumno();
        Profesor profesor = new Profesor();
        AlumnoDto alumnoDto = new AlumnoDto();
        ProfesorDto profesorDto = new ProfesorDto();

        given(alumnoRepository.findAll()).willReturn(List.of(alumno));
        given(profesorRepository.findAll()).willReturn(List.of(profesor));
        given(alumnoMapper.toAlumnoDtoList(List.of(alumno))).willReturn(List.of(alumnoDto));
        given(profesorMapper.toProfesorDtoList(List.of(profesor))).willReturn(List.of(profesorDto));

        // when
        List<Object> result = usuarioService.obtenerUsuarios(null);

        // then
        assertEquals(2, result.size());
        assertTrue(result.contains(alumnoDto));
        assertTrue(result.contains(profesorDto));
    }

    @Test
    void givenTipoAlumno_whenObtenerUsuarios_thenReturnAlumnosOnly() {
        // given
        Alumno alumno = new Alumno();
        AlumnoDto alumnoDto = new AlumnoDto();

        given(alumnoRepository.findAll()).willReturn(List.of(alumno));
        given(alumnoMapper.toAlumnoDtoList(List.of(alumno))).willReturn(List.of(alumnoDto));

        // when
        List<Object> result = usuarioService.obtenerUsuarios("alumno");

        // then
        assertEquals(1, result.size());
        assertTrue(result.contains(alumnoDto));
    }

    @Test
    void givenTipoProfesor_whenObtenerUsuarios_thenReturnProfesoresOnly() {
        // given
        Profesor profesor = new Profesor();
        ProfesorDto profesorDto = new ProfesorDto();

        given(profesorRepository.findAll()).willReturn(List.of(profesor));
        given(profesorMapper.toProfesorDtoList(List.of(profesor))).willReturn(List.of(profesorDto));

        // when
        List<Object> result = usuarioService.obtenerUsuarios("profesor");

        // then
        assertEquals(1, result.size());
        assertTrue(result.contains(profesorDto));
    }

    @Test
    void givenAlumnoDto_whenCrearUsuario_thenReturnAlumnoDto() {
        // given
        AlumnoDto inputDto = new AlumnoDto();
        Alumno alumno = new Alumno();
        AlumnoDto outputDto = new AlumnoDto();

        given(alumnoMapper.toAlumnoEntity(inputDto)).willReturn(alumno);
        given(alumnoRepository.save(alumno)).willReturn(alumno);
        given(alumnoMapper.toAlumnoDto(alumno)).willReturn(outputDto);

        // when
        Object result = usuarioService.crearUsuario(inputDto);

        // then
        assertEquals(outputDto, result);
    }

    @Test
    void givenProfesorDto_whenCrearUsuario_thenReturnProfesorDto() {
        // given
        ProfesorDto inputDto = new ProfesorDto();
        Profesor profesor = new Profesor();
        ProfesorDto outputDto = new ProfesorDto();

        given(profesorMapper.toProfesorEntity(inputDto)).willReturn(profesor);
        given(profesorRepository.save(profesor)).willReturn(profesor);
        given(profesorMapper.toProfesorDto(profesor)).willReturn(outputDto);

        // when
        Object result = usuarioService.crearUsuario(inputDto);

        // then
        assertEquals(outputDto, result);
    }

    @Test
    void givenInvalidObject_whenCrearUsuario_thenThrowException() {
        // when & then
        assertThrows(IllegalArgumentException.class, () -> usuarioService.crearUsuario("invalido"));
    }

    @Test
    void givenAlumnoId_whenEliminarUsuario_thenAlumnoDeleted() {
        // given
        given(alumnoRepository.existsById(1L)).willReturn(true);

        // when
        usuarioService.eliminarUsuario(1L);

        // then
        then(alumnoRepository).should().deleteById(1L);
    }

    @Test
    void givenProfesorId_whenEliminarUsuario_thenProfesorDeleted() {
        // given
        given(alumnoRepository.existsById(1L)).willReturn(false);
        given(profesorRepository.existsById(1L)).willReturn(true);

        // when
        usuarioService.eliminarUsuario(1L);

        // then
        then(profesorRepository).should().deleteById(1L);
    }

    @Test
    void givenInvalidId_whenEliminarUsuario_thenThrowException() {
        // given
        given(alumnoRepository.existsById(1L)).willReturn(false);
        given(profesorRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> usuarioService.eliminarUsuario(1L));
    }

    @Test
    void givenKeycloakId_whenGetUsuarioByKeycloakId_thenReturnUsuario() {
        // given
        Usuario usuario = new Alumno();
        given(usuarioRepository.findByIdKeycloak("abc")).willReturn(Optional.of(usuario));

        // when
        Usuario result = usuarioService.getUsuarioByKeycloakId("abc");

        // then
        assertEquals(usuario, result);
    }

    @Test
    void givenInvalidKeycloakId_whenGetUsuarioByKeycloakId_thenThrowException() {
        // given
        given(usuarioRepository.findByIdKeycloak("abc")).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> usuarioService.getUsuarioByKeycloakId("abc"));
    }
}
