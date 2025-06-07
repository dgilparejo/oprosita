package com.oprosita.backend.controller.api;

import com.oprosita.backend.dto.ArchivoDto;
import com.oprosita.backend.model.Archivo;
import com.oprosita.backend.service.ArchivoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ArchivoControllerTest {

    @Mock
    private ArchivoService archivoService;

    @InjectMocks
    private ArchivoController archivoController;

    @Test
    void givenFile_whenUploadArchivo_thenReturnsCreatedArchivo() {
        // given
        MultipartFile file = mock(MultipartFile.class);
        ArchivoDto dto = ArchivoDto.builder()
                .id(1)
                .nombre("documento.pdf")
                .tipo("application/pdf")
                .url("/archivos/documento.pdf")
                .datos(new byte[]{1, 2, 3})
                .build();

        given(archivoService.subirArchivo(file)).willReturn(dto);

        // when
        ResponseEntity<com.oprosita.backend.model.generated.Archivo> response = archivoController.uploadArchivo(file);

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNombre()).isEqualTo(dto.getNombre());
        then(archivoService).should().subirArchivo(file);
    }

    @Test
    void givenArchivoId_whenDownloadArchivo_thenReturnsResource() {
        // given
        Long id = 1L;
        Archivo archivo = Archivo.builder()
                .id((long) id.intValue())
                .nombre("archivo.pdf")
                .tipo("application/pdf")
                .datos(new byte[]{1, 2, 3})
                .build();

        given(archivoService.obtenerEntidadPorId(id)).willReturn(archivo);

        // when
        ResponseEntity<Resource> response = archivoController.downloadArchivo(id.intValue());

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isInstanceOf(ByteArrayResource.class);
        then(archivoService).should().obtenerEntidadPorId(id);
    }

    @Test
    void givenArchivoId_whenGetArchivoInfo_thenReturnsArchivoInfo() {
        // given
        Long id = 1L;
        ArchivoDto dto = ArchivoDto.builder()
                .id(1)
                .nombre("info.pdf")
                .tipo("application/pdf")
                .url("/archivos/info.pdf")
                .build();

        given(archivoService.obtenerPorId(id)).willReturn(dto);

        // when
        ResponseEntity<com.oprosita.backend.model.generated.Archivo> response = archivoController.getArchivoInfo(id.intValue());

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNombre()).isEqualTo(dto.getNombre());
        then(archivoService).should().obtenerPorId(id);
    }
}
