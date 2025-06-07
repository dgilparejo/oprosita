package com.oprosita.backend.service.impl;

import com.oprosita.backend.model.Alumno;
import com.oprosita.backend.model.Profesor;
import com.oprosita.backend.model.Usuario;
import com.oprosita.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioAutoServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioAutoServiceImpl usuarioAutoService;

    @Test
    void givenExistingUser_whenEnsure_thenReturnExisting() {
        // given
        Usuario usuario = new Alumno();
        given(usuarioRepository.findByIdKeycloak("abc123")).willReturn(Optional.of(usuario));

        // when
        Usuario result = usuarioAutoService.ensureUsuarioExists("abc123", "nombre", "alumno");

        // then
        assertEquals(usuario, result);
        then(usuarioRepository).should().findByIdKeycloak("abc123");
        then(usuarioRepository).should(never()).save(any());
    }

    @Test
    void givenNewAlumno_whenEnsure_thenCreatesAlumno() {
        // given
        given(usuarioRepository.findByIdKeycloak("new123")).willReturn(Optional.empty());
        given(usuarioRepository.save(any(Alumno.class))).willAnswer(inv -> inv.getArgument(0));

        // when
        Usuario result = usuarioAutoService.ensureUsuarioExists("new123", "Juan", "alumno");

        // then
        assertTrue(result instanceof Alumno);
        assertEquals("new123", result.getIdKeycloak());
        assertEquals("Juan", result.getNombre());
    }

    @Test
    void givenNewProfesor_whenEnsure_thenCreatesProfesor() {
        // given
        given(usuarioRepository.findByIdKeycloak("prof123")).willReturn(Optional.empty());
        given(usuarioRepository.save(any(Profesor.class))).willAnswer(inv -> inv.getArgument(0));

        // when
        Usuario result = usuarioAutoService.ensureUsuarioExists("prof123", "Ana", "profesor");

        // then
        assertTrue(result instanceof Profesor);
        assertEquals("prof123", result.getIdKeycloak());
        assertEquals("Ana", result.getNombre());
    }
}
