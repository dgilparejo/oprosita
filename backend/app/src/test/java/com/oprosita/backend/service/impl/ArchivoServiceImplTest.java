package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ArchivoDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.ArchivoMapper;
import com.oprosita.backend.model.Archivo;
import com.oprosita.backend.repository.ArchivoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ArchivoServiceImplTest {

    @Mock
    private ArchivoRepository archivoRepository;

    @Mock
    private ArchivoMapper archivoMapper;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ArchivoServiceImpl archivoService;

    @Test
    void givenId_whenObtenerPorId_thenReturnArchivoDto() {
        // given
        Archivo archivo = new Archivo();
        ArchivoDto archivoDto = new ArchivoDto();

        given(archivoRepository.findById(1L)).willReturn(Optional.of(archivo));
        given(archivoMapper.toArchivoDto(archivo)).willReturn(archivoDto);

        // when
        ArchivoDto result = archivoService.obtenerPorId(1L);

        // then
        assertNotNull(result);
        assertEquals(archivoDto, result);
        then(archivoRepository).should().findById(1L);
        then(archivoMapper).should().toArchivoDto(archivo);
    }

    @Test
    void givenId_whenObtenerPorId_thenThrowNotFoundException() {
        // given
        given(archivoRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> archivoService.obtenerPorId(1L));
        then(archivoRepository).should().findById(1L);
    }

    @Test
    void whenObtenerTodos_thenReturnListOfArchivoDto() {
        // given
        Archivo archivo1 = new Archivo();
        Archivo archivo2 = new Archivo();
        ArchivoDto dto1 = new ArchivoDto();
        ArchivoDto dto2 = new ArchivoDto();

        given(archivoRepository.findAll()).willReturn(List.of(archivo1, archivo2));
        given(archivoMapper.toArchivoDto(archivo1)).willReturn(dto1);
        given(archivoMapper.toArchivoDto(archivo2)).willReturn(dto2);

        // when
        List<ArchivoDto> result = archivoService.obtenerTodos();

        // then
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        then(archivoRepository).should().findAll();
    }

    @Test
    void givenArchivoDto_whenCrear_thenReturnArchivoDto() {
        // given
        ArchivoDto dto = new ArchivoDto();
        Archivo archivo = new Archivo();
        Archivo savedArchivo = new Archivo();
        ArchivoDto resultDto = new ArchivoDto();

        given(archivoMapper.toArchivoEntity(dto)).willReturn(archivo);
        given(archivoRepository.save(archivo)).willReturn(savedArchivo);
        given(archivoMapper.toArchivoDto(savedArchivo)).willReturn(resultDto);

        // when
        ArchivoDto result = archivoService.crear(dto);

        // then
        assertEquals(resultDto, result);
        then(archivoMapper).should().toArchivoEntity(dto);
        then(archivoRepository).should().save(archivo);
        then(archivoMapper).should().toArchivoDto(savedArchivo);
    }

    @Test
    void givenIdAndDto_whenActualizar_thenReturnUpdatedArchivoDto() {
        // given
        Long id = 1L;
        ArchivoDto dto = new ArchivoDto();
        Archivo archivo = new Archivo();
        archivo.setId(id);
        Archivo updatedArchivo = new Archivo();
        ArchivoDto resultDto = new ArchivoDto();

        given(archivoRepository.existsById(id)).willReturn(true);
        given(archivoMapper.toArchivoEntity(dto)).willReturn(archivo);
        given(archivoRepository.save(archivo)).willReturn(updatedArchivo);
        given(archivoMapper.toArchivoDto(updatedArchivo)).willReturn(resultDto);

        // when
        ArchivoDto result = archivoService.actualizar(id, dto);

        // then
        assertEquals(resultDto, result);
        then(archivoRepository).should().existsById(id);
        then(archivoMapper).should().toArchivoEntity(dto);
        then(archivoRepository).should().save(archivo);
        then(archivoMapper).should().toArchivoDto(updatedArchivo);
    }

    @Test
    void givenNonExistingId_whenActualizar_thenThrowNotFoundException() {
        // given
        Long id = 1L;
        ArchivoDto dto = new ArchivoDto();

        given(archivoRepository.existsById(id)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> archivoService.actualizar(id, dto));
        then(archivoRepository).should().existsById(id);
        then(archivoRepository).shouldHaveNoMoreInteractions();
        then(archivoMapper).shouldHaveNoInteractions();
    }

    @Test
    void givenId_whenEliminar_thenArchivoDeleted() {
        // given
        given(archivoRepository.existsById(1L)).willReturn(true);

        // when
        archivoService.eliminar(1L);

        // then
        then(archivoRepository).should().existsById(1L);
        then(archivoRepository).should().deleteById(1L);
    }

    @Test
    void givenId_whenEliminar_thenThrowNotFoundException() {
        // given
        given(archivoRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> archivoService.eliminar(1L));
        then(archivoRepository).should().existsById(1L);
        then(archivoRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void givenValidFile_whenSubirArchivo_thenReturnArchivoDto() throws IOException {
        // given
        byte[] fileBytes = "data".getBytes();
        Archivo archivo = new Archivo();
        Archivo savedArchivo = new Archivo();
        ArchivoDto resultDto = new ArchivoDto();

        given(multipartFile.isEmpty()).willReturn(false);
        given(multipartFile.getOriginalFilename()).willReturn("test.pdf");
        given(multipartFile.getContentType()).willReturn("application/pdf");
        given(multipartFile.getBytes()).willReturn(fileBytes);
        given(archivoRepository.save(any(Archivo.class))).willReturn(savedArchivo);
        given(archivoMapper.toArchivoDto(savedArchivo)).willReturn(resultDto);

        // when
        ArchivoDto result = archivoService.subirArchivo(multipartFile);

        // then
        assertEquals(resultDto, result);
        then(archivoRepository).should().save(any(Archivo.class));
        then(archivoMapper).should().toArchivoDto(savedArchivo);
    }

    @Test
    void givenNullFile_whenSubirArchivo_thenThrowIllegalArgumentException() {
        // when & then
        assertThrows(IllegalArgumentException.class, () -> archivoService.subirArchivo(null));
    }

    @Test
    void givenArchivoId_whenDescargarArchivo_thenReturnResource() {
        // given
        Archivo archivo = Archivo.builder().datos("contenido".getBytes()).build();
        given(archivoRepository.findById(1L)).willReturn(Optional.of(archivo));

        // when
        Resource result = archivoService.descargarArchivo(1L);

        // then
        assertNotNull(result);
        assertTrue(result.exists());
        then(archivoRepository).should().findById(1L);
    }

    @Test
    void givenArchivoId_whenObtenerEntidadPorId_thenReturnArchivo() {
        // given
        Archivo archivo = new Archivo();
        given(archivoRepository.findById(1L)).willReturn(Optional.of(archivo));

        // when
        Archivo result = archivoService.obtenerEntidadPorId(1L);

        // then
        assertEquals(archivo, result);
        then(archivoRepository).should().findById(1L);
    }

    @Test
    void givenArchivoId_whenObtenerEntidadPorId_thenThrowNotFoundException() {
        // given
        given(archivoRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> archivoService.obtenerEntidadPorId(1L));
        then(archivoRepository).should().findById(1L);
    }
}
