package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ArchivoDto;
import com.oprosita.backend.dto.NoticiaDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.NoticiaMapper;
import com.oprosita.backend.model.Archivo;
import com.oprosita.backend.model.Noticia;
import com.oprosita.backend.repository.ArchivoRepository;
import com.oprosita.backend.repository.NoticiaRepository;
import com.oprosita.backend.service.ArchivoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class NoticiaServiceImplTest {

    @Mock
    private NoticiaRepository noticiaRepository;

    @Mock
    private ArchivoService archivoService;

    @Mock
    private ArchivoRepository archivoRepository;

    @Mock
    private NoticiaMapper noticiaMapper;

    @InjectMocks
    private NoticiaServiceImpl noticiaService;

    @Test
    void givenId_whenObtenerPorId_thenReturnsDto() {
        // given
        Noticia noticia = new Noticia();
        NoticiaDto dto = new NoticiaDto();
        given(noticiaRepository.findById(1L)).willReturn(Optional.of(noticia));
        given(noticiaMapper.toNoticiaDto(noticia)).willReturn(dto);

        // when
        NoticiaDto result = noticiaService.obtenerPorId(1L);

        // then
        assertEquals(dto, result);
        then(noticiaRepository).should().findById(1L);
        then(noticiaMapper).should().toNoticiaDto(noticia);
    }

    @Test
    void givenId_whenObtenerPorId_thenThrowsNotFound() {
        // given
        given(noticiaRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> noticiaService.obtenerPorId(1L));
        then(noticiaRepository).should().findById(1L);
    }

    @Test
    void whenObtenerTodos_thenReturnsListOfDtos() {
        // given
        Noticia noticia1 = new Noticia();
        Noticia noticia2 = new Noticia();
        NoticiaDto dto1 = new NoticiaDto();
        NoticiaDto dto2 = new NoticiaDto();

        given(noticiaRepository.findAll()).willReturn(List.of(noticia1, noticia2));
        given(noticiaMapper.toNoticiaDto(noticia1)).willReturn(dto1);
        given(noticiaMapper.toNoticiaDto(noticia2)).willReturn(dto2);

        // when
        List<NoticiaDto> result = noticiaService.obtenerTodos();

        // then
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        then(noticiaRepository).should().findAll();
    }

    @Test
    void givenDto_whenCrear_thenReturnsCreatedDto() {
        // given
        NoticiaDto inputDto = new NoticiaDto();
        Noticia noticia = new Noticia();
        Noticia saved = new Noticia();
        NoticiaDto outputDto = new NoticiaDto();

        given(noticiaMapper.toNoticiaEntity(inputDto)).willReturn(noticia);
        given(noticiaRepository.save(noticia)).willReturn(saved);
        given(noticiaMapper.toNoticiaDto(saved)).willReturn(outputDto);

        // when
        NoticiaDto result = noticiaService.crear(inputDto);

        // then
        assertEquals(outputDto, result);
        then(noticiaMapper).should().toNoticiaEntity(inputDto);
        then(noticiaRepository).should().save(noticia);
        then(noticiaMapper).should().toNoticiaDto(saved);
    }

    @Test
    void givenValidIdAndDto_whenActualizar_thenReturnsUpdatedDto() {
        // given
        Long id = 1L;
        NoticiaDto dto = new NoticiaDto();
        Noticia noticia = new Noticia();
        Noticia saved = new Noticia();
        NoticiaDto expected = new NoticiaDto();

        given(noticiaRepository.existsById(id)).willReturn(true);
        given(noticiaMapper.toNoticiaEntity(dto)).willReturn(noticia);
        given(noticiaRepository.save(noticia)).willReturn(saved);
        given(noticiaMapper.toNoticiaDto(saved)).willReturn(expected);

        // when
        NoticiaDto result = noticiaService.actualizar(id, dto);

        // then
        assertEquals(expected, result);
        then(noticiaRepository).should().existsById(id);
        then(noticiaMapper).should().toNoticiaEntity(dto);
        then(noticiaRepository).should().save(noticia);
        then(noticiaMapper).should().toNoticiaDto(saved);
    }

    @Test
    void givenNonExistingId_whenActualizar_thenThrowsNotFound() {
        // given
        NoticiaDto dto = new NoticiaDto();
        given(noticiaRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> noticiaService.actualizar(1L, dto));
        then(noticiaRepository).should().existsById(1L);
        then(noticiaRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void givenExistingId_whenEliminar_thenDeletes() {
        // given
        given(noticiaRepository.existsById(1L)).willReturn(true);

        // when
        noticiaService.eliminar(1L);

        // then
        then(noticiaRepository).should().existsById(1L);
        then(noticiaRepository).should().deleteById(1L);
    }

    @Test
    void givenNonExistingId_whenEliminar_thenThrowsNotFound() {
        // given
        given(noticiaRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> noticiaService.eliminar(1L));
        then(noticiaRepository).should().existsById(1L);
    }

    @Test
    void givenDescripcionAndUrlAndArchivo_whenCrearNoticia_thenReturnsDto() {
        // given
        MultipartFile file = mock(MultipartFile.class);
        ArchivoDto archivoDto = new ArchivoDto();
        archivoDto.setId(42);

        Archivo archivo = new Archivo();
        Noticia noticia = new Noticia();
        NoticiaDto expectedDto = new NoticiaDto();

        given(file.isEmpty()).willReturn(false);
        given(archivoService.subirArchivo(file)).willReturn(archivoDto);
        given(archivoRepository.findById(anyLong())).willReturn(Optional.of(archivo));
        given(noticiaRepository.save(any())).willReturn(noticia);
        given(noticiaMapper.toNoticiaDto(noticia)).willReturn(expectedDto);

        // when
        NoticiaDto result = noticiaService.crearNoticia("desc", URI.create("http://example.com"), file);

        // then
        assertEquals(expectedDto, result);
        then(archivoService).should().subirArchivo(file);
        then(archivoRepository).should().findById(anyLong());
        then(noticiaRepository).should().save(any(Noticia.class));
        then(noticiaMapper).should().toNoticiaDto(noticia);
    }
}
