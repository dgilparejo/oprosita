package com.oprosita.backend.controller.api;

import com.oprosita.backend.dto.NoticiaDto;
import com.oprosita.backend.mapper.NoticiaMapper;
import com.oprosita.backend.model.generated.Noticia;
import com.oprosita.backend.service.NoticiaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class NoticiaControllerTest {

    @Mock
    private NoticiaService noticiaService;

    @Mock
    private NoticiaMapper mapper;

    @InjectMocks
    private NoticiaController noticiaController;

    @Test
    void givenNoticiaId_whenDeleteNoticia_thenReturnsNoContent() {
        // given
        Integer id = 1;
        willDoNothing().given(noticiaService).eliminar(id.longValue());

        // when
        ResponseEntity<Void> response = noticiaController.deleteNoticia(id);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(204);
        then(noticiaService).should().eliminar(id.longValue());
    }

    @Test
    void whenGetNoticias_thenReturnsList() {
        // given
        NoticiaDto dto = new NoticiaDto();
        Noticia noticia = new Noticia();
        given(noticiaService.obtenerTodos()).willReturn(List.of(dto));
        given(mapper.toGeneratedNoticia(dto)).willReturn(noticia);

        // when
        ResponseEntity<List<Noticia>> response = noticiaController.getNoticias();

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).containsExactly(noticia);
    }

    @Test
    void whenCreateNoticia_thenReturnsCreated() {
        // given
        String descripcion = "Descripción";
        URI uri = URI.create("http://test.com");
        MultipartFile file = null;
        NoticiaDto dto = new NoticiaDto();
        Noticia noticia = new Noticia();

        given(noticiaService.crearNoticia(descripcion, uri, file)).willReturn(dto);
        given(mapper.toGeneratedNoticia(dto)).willReturn(noticia);

        // when
        ResponseEntity<Noticia> response = noticiaController.createNoticia(descripcion, uri, file);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(noticia);
    }
}
