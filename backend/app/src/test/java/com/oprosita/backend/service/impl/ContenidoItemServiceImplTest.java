package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ArchivoDto;
import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.dto.NovedadDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.ArchivoMapper;
import com.oprosita.backend.mapper.ContenidoItemMapper;
import com.oprosita.backend.model.*;
import com.oprosita.backend.repository.*;
import com.oprosita.backend.service.ArchivoService;
import com.oprosita.backend.service.NovedadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ContenidoItemServiceImplTest {

    @Mock private ContenidoItemRepository contenidoItemRepository;
    @Mock private GrupoRepository grupoRepository;
    @Mock private AlumnoRepository alumnoRepository;
    @Mock private ArchivoRepository archivoRepository;
    @Mock private MesRepository mesRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private ContenidoItemMapper contenidoItemMapper;
    @Mock private ArchivoMapper archivoMapper;
    @Mock private ArchivoService archivoService;
    @Mock private NovedadService novedadService;

    @InjectMocks
    private ContenidoItemServiceImpl contenidoItemService;

    @Test
    void givenId_whenGetContenido_thenReturnContenido() {
        // given
        ContenidoItem contenido = new ContenidoItem();
        ContenidoItemDto contenidoDto = new ContenidoItemDto();

        given(contenidoItemRepository.findById(1L)).willReturn(Optional.of(contenido));
        given(contenidoItemMapper.toContenidoItemDto(contenido)).willReturn(contenidoDto);

        // when
        ContenidoItemDto result = contenidoItemService.obtenerPorId(1L);

        // then
        assertNotNull(result);
        assertEquals(contenidoDto, result);
        then(contenidoItemRepository).should().findById(1L);
        then(contenidoItemMapper).should().toContenidoItemDto(contenido);
    }

    @Test
    void givenId_whenGetContenido_thenThrowNotFound() {
        // given
        given(contenidoItemRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> contenidoItemService.obtenerPorId(1L));
        then(contenidoItemRepository).should().findById(1L);
    }

    @Test
    void givenContenido_whenCreate_thenReturnContenido() {
        // given
        ContenidoItemDto inputDto = new ContenidoItemDto();
        ContenidoItem contenidoEntity = new ContenidoItem();
        ContenidoItem savedEntity = new ContenidoItem();
        ContenidoItemDto outputDto = new ContenidoItemDto();

        given(contenidoItemMapper.toContenidoItemEntity(inputDto)).willReturn(contenidoEntity);
        given(contenidoItemRepository.save(contenidoEntity)).willReturn(savedEntity);
        given(contenidoItemMapper.toContenidoItemDto(savedEntity)).willReturn(outputDto);

        // when
        ContenidoItemDto result = contenidoItemService.crear(inputDto);

        // then
        assertEquals(outputDto, result);
        then(contenidoItemMapper).should().toContenidoItemEntity(inputDto);
        then(contenidoItemRepository).should().save(contenidoEntity);
        then(contenidoItemMapper).should().toContenidoItemDto(savedEntity);
    }

    @Test
    void givenNonExistingId_whenUpdate_thenThrowException() {
        // given
        ContenidoItemDto dto = new ContenidoItemDto();
        given(contenidoItemRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> contenidoItemService.actualizar(1L, dto));
        then(contenidoItemRepository).should().existsById(1L);
    }

    @Test
    void givenExistingId_whenDeleteByDifferentUser_thenThrowAccessDenied() {
        // given
        ContenidoItem contenido = new ContenidoItem();
        Alumno autor = new Alumno();
        autor.setId(2L);
        contenido.setAutor(autor);

        given(contenidoItemRepository.findById(1L)).willReturn(Optional.of(contenido));

        // when & then
        assertThrows(AccessDeniedException.class, () -> contenidoItemService.eliminar(1L, 99L));
        then(contenidoItemRepository).should().findById(1L);
    }

    @Test
    void givenValidIdAndUser_whenDelete_thenDeleteContenido() {
        // given
        ContenidoItem contenido = new ContenidoItem();
        Archivo archivo = new Archivo();
        archivo.setId(10L);
        contenido.setArchivo(archivo);
        Alumno autor = new Alumno();
        autor.setId(1L);
        contenido.setAutor(autor);

        given(contenidoItemRepository.findById(1L)).willReturn(Optional.of(contenido));

        // when
        contenidoItemService.eliminar(1L, 1L);

        // then
        then(contenidoItemRepository).should().findById(1L);
        then(archivoRepository).should().deleteById(10L);
        then(contenidoItemRepository).should().deleteById(1L);
    }
}
