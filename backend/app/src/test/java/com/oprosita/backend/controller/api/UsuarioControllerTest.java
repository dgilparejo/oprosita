package com.oprosita.backend.controller.api;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.ProfesorDto;
import com.oprosita.backend.mapper.UsuarioMapper;
import com.oprosita.backend.model.Alumno;
import com.oprosita.backend.model.generated.Usuario;
import com.oprosita.backend.model.generated.CrearUsuario201Response;
import com.oprosita.backend.model.generated.CrearUsuarioRequest;
import com.oprosita.backend.service.UsuarioService;
import com.oprosita.backend.util.AuthUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    void givenTipo_whenObtenerUsuarios_thenReturnsUsuarioList() {
        // given
        String tipo = "alumno";
        Object dto = new AlumnoDto();
        Usuario genUsuario = new Usuario();

        given(usuarioService.obtenerUsuarios(tipo)).willReturn(List.of(dto));
        given(usuarioMapper.mapToUsuarioList(List.of(dto))).willReturn(List.of(genUsuario));

        // when
        ResponseEntity<List<Usuario>> response = usuarioController.obtenerUsuarios(tipo);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(genUsuario);
    }

    @Test
    void givenCrearUsuarioRequest_whenCrearUsuario_thenReturns201Response() {
        // given
        CrearUsuarioRequest request = new CrearUsuarioRequest();
        Object dto = new ProfesorDto();
        Object creado = new ProfesorDto();
        CrearUsuario201Response responseDto = new CrearUsuario201Response();

        given(usuarioMapper.mapToDto(request)).willReturn(dto);
        given(usuarioService.crearUsuario(dto)).willReturn(creado);
        given(usuarioMapper.mapToCrearUsuario201Response(creado)).willReturn(responseDto);

        // when
        ResponseEntity<CrearUsuario201Response> response = usuarioController.crearUsuario(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(responseDto);
    }

    @Test
    void givenUsuarioId_whenEliminarUsuario_thenReturnsNoContent() {
        // given
        Integer id = 5;
        willDoNothing().given(usuarioService).eliminarUsuario(id.longValue());

        // when
        ResponseEntity<Void> response = usuarioController.eliminarUsuario(id);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        then(usuarioService).should().eliminarUsuario(id.longValue());
    }

    @Test
    void whenGetMiUsuario_withValidKeycloakId_thenReturnsUsuarioResponse() {
        // given
        String keycloakId = "abc123";
        Alumno usuario = new Alumno();
        CrearUsuario201Response responseDto = new CrearUsuario201Response();

        try (MockedStatic<AuthUtils> mockedStatic = mockStatic(AuthUtils.class)) {
            mockedStatic.when(AuthUtils::getUsuarioId).thenReturn(keycloakId);

            given(usuarioService.getUsuarioByKeycloakId(keycloakId)).willReturn(usuario);
            given(usuarioMapper.fromUsuario(usuario)).willReturn(responseDto);

            // when
            ResponseEntity<CrearUsuario201Response> response = usuarioController.getMiUsuario();

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(responseDto);
        }
    }

    @Test
    void whenGetMiUsuario_withNullKeycloakId_thenReturnsUnauthorized() {
        // given
        try (MockedStatic<AuthUtils> mockedStatic = mockStatic(AuthUtils.class)) {
            mockedStatic.when(AuthUtils::getUsuarioId).thenReturn(null);

            // when
            ResponseEntity<CrearUsuario201Response> response = usuarioController.getMiUsuario();

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }

    @Test
    void whenGetMiUsuario_withNotFoundUsuario_thenReturnsNotFound() {
        // given
        String keycloakId = "not_found";
        try (MockedStatic<AuthUtils> mockedStatic = mockStatic(AuthUtils.class)) {
            mockedStatic.when(AuthUtils::getUsuarioId).thenReturn(keycloakId);
            given(usuarioService.getUsuarioByKeycloakId(keycloakId)).willReturn(null);

            // when
            ResponseEntity<CrearUsuario201Response> response = usuarioController.getMiUsuario();

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }
}
