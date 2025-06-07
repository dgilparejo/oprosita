package com.oprosita.backend.controller.api;

import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.mapper.ContenidoItemMapper;
import com.oprosita.backend.model.Alumno;
import com.oprosita.backend.model.TipoContenido;
import com.oprosita.backend.model.Usuario;
import com.oprosita.backend.model.generated.ContenidoItem;
import com.oprosita.backend.service.ContenidoItemService;
import com.oprosita.backend.service.UsuarioService;
import com.oprosita.backend.util.AuthUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ContenidoItemControllerTest {

    @Mock
    private ContenidoItemService contenidoItemService;

    @Mock
    private ContenidoItemMapper mapper;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private ContenidoItemController controller;

    @Test
    void givenAlumnoIdAndData_whenAddContenidoPrivadoAlumno_thenReturnsCreatedContenidoItem() {
        // given
        Integer alumnoId = 1;
        String texto = "Texto de prueba";
        String tipoContenido = "TEMA";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Contenido".getBytes());

        ContenidoItemDto dto = ContenidoItemDto.builder().texto(texto).build();
        ContenidoItem generado = new ContenidoItem().texto(texto);

        given(contenidoItemService.crearParaAlumno(1L, texto, tipoContenido, file)).willReturn(dto);
        given(mapper.toGeneratedContenidoItem(dto)).willReturn(generado);

        // when
        ResponseEntity<ContenidoItem> response = controller.addContenidoPrivadoAlumno(alumnoId, texto, tipoContenido, file);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(generado);
        then(contenidoItemService).should().crearParaAlumno(1L, texto, tipoContenido, file);
        then(mapper).should().toGeneratedContenidoItem(dto);
    }

    @Test
    void givenGrupoAndMes_whenAddContenidoToGrupoMes_thenReturnsCreatedContenidoItem() {
        // given
        Integer grupoId = 1, mesId = 2, autorId = 3;
        String texto = "Hola", tipo = "TEMAS";
        MockMultipartFile file = new MockMultipartFile("file", "archivo.pdf", "application/pdf", "PDF".getBytes());

        ContenidoItemDto dto = ContenidoItemDto.builder().texto(texto).build();
        ContenidoItem generado = new ContenidoItem().texto(texto);

        given(contenidoItemService.crearParaGrupoPorMes(eq(grupoId.longValue()), eq(mesId.longValue()), any(ContenidoItemDto.class), eq(file)))
                .willReturn(dto);
        given(mapper.toGeneratedContenidoItem(dto)).willReturn(generado);

        // when
        ResponseEntity<ContenidoItem> response = controller.addContenidoToGrupoMes(grupoId, mesId, texto, tipo, autorId, file);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(generado);
        then(contenidoItemService).should().crearParaGrupoPorMes(eq(1L), eq(2L), any(), eq(file));
        then(mapper).should().toGeneratedContenidoItem(dto);
    }

    @Test
    void givenAlumnoId_whenGetContenidoPrivadoAlumno_thenReturnsContenidoList() {
        // given
        Integer alumnoId = 5;
        ContenidoItemDto dto1 = ContenidoItemDto.builder().texto("A").build();
        ContenidoItemDto dto2 = ContenidoItemDto.builder().texto("B").build();
        ContenidoItem c1 = new ContenidoItem().texto("A");
        ContenidoItem c2 = new ContenidoItem().texto("B");

        given(contenidoItemService.obtenerPorAlumno(alumnoId.longValue())).willReturn(List.of(dto1, dto2));
        given(mapper.toGeneratedContenidoItem(dto1)).willReturn(c1);
        given(mapper.toGeneratedContenidoItem(dto2)).willReturn(c2);

        // when
        ResponseEntity<List<ContenidoItem>> response = controller.getContenidoPrivadoAlumno(alumnoId);

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).containsExactly(c1, c2);
        then(contenidoItemService).should().obtenerPorAlumno(5L);
        then(mapper).should(times(2)).toGeneratedContenidoItem(any());
    }

    @Test
    void givenGrupoAndMes_whenGetContenidoGrupoMes_thenReturnsContenidoList() {
        // given
        Integer grupoId = 10, mesId = 20;
        ContenidoItemDto dto = ContenidoItemDto.builder().texto("X").build();
        ContenidoItem contenido = new ContenidoItem().texto("X");

        given(contenidoItemService.obtenerPorGrupoYMes(grupoId.longValue(), String.valueOf(mesId))).willReturn(List.of(dto));
        given(mapper.toGeneratedContenidoItem(dto)).willReturn(contenido);

        // when
        ResponseEntity<List<ContenidoItem>> response = controller.getContenidoGrupoMes(grupoId, mesId);

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).containsExactly(contenido);
        then(contenidoItemService).should().obtenerPorGrupoYMes(10L, "20");
        then(mapper).should().toGeneratedContenidoItem(dto);
    }

    @Test
    void givenValidUser_whenDeleteContenido_thenReturnsNoContent() {
        // given
        Integer id = 5;
        String keycloakId = "keycloak123";
        Alumno usuario = new Alumno();
        usuario.setId(99L);

        try (MockedStatic<AuthUtils> mocked = mockStatic(AuthUtils.class)) {
            mocked.when(AuthUtils::getUsuarioId).thenReturn(keycloakId);
            given(usuarioService.getUsuarioByKeycloakId(keycloakId)).willReturn(usuario);
            willDoNothing().given(contenidoItemService).eliminar(id.longValue(), usuario.getId());

            // when
            ResponseEntity<Void> response = controller.deleteContenido(id);

            // then
            assertThat(response.getStatusCode().value()).isEqualTo(204);
            then(contenidoItemService).should().eliminar(5L, 99L);
        }
    }
}
