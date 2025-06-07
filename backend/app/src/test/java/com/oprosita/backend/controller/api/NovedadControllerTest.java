package com.oprosita.backend.controller.api;

import com.oprosita.backend.dto.NovedadDto;
import com.oprosita.backend.mapper.NovedadMapper;
import com.oprosita.backend.model.generated.Novedad;
import com.oprosita.backend.service.NovedadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class NovedadControllerTest {

    @Mock
    private NovedadService novedadService;

    @Mock
    private NovedadMapper mapper;

    @InjectMocks
    private NovedadController novedadController;

    @Test
    void givenNovedadId_whenDelete_thenReturnsNoContent() {
        Integer id = 1;
        willDoNothing().given(novedadService).eliminar(id.longValue());

        ResponseEntity<Void> response = novedadController.deleteNovedad(id);

        assertThat(response.getStatusCode().value()).isEqualTo(204);
        then(novedadService).should().eliminar(id.longValue());
    }

    @Test
    void whenGetNovedadesAlumno_thenReturnsList() {
        NovedadDto dto = new NovedadDto();
        Novedad novedad = new Novedad();

        given(novedadService.obtenerNovedadesAlumno()).willReturn(List.of(dto));
        given(mapper.toGeneratedNovedad(dto)).willReturn(novedad);

        ResponseEntity<List<Novedad>> response = novedadController.getNovedadesAlumno();

        assertThat(response.getBody()).containsExactly(novedad);
    }

    @Test
    void whenGetNovedadesProfesorWithoutDate_thenReturnsAll() {
        NovedadDto dto = new NovedadDto();
        Novedad novedad = new Novedad();

        given(novedadService.obtenerNovedadesProfesor()).willReturn(List.of(dto));
        given(mapper.toGeneratedNovedad(dto)).willReturn(novedad);

        ResponseEntity<List<Novedad>> response = novedadController.getNovedadesProfesor(null);

        assertThat(response.getBody()).containsExactly(novedad);
    }

    @Test
    void whenGetNovedadesProfesorWithDate_thenReturnsFiltered() {
        OffsetDateTime date = OffsetDateTime.now();
        NovedadDto dto = new NovedadDto();
        Novedad novedad = new Novedad();

        given(novedadService.obtenerNovedadesProfesorDesde(date)).willReturn(List.of(dto));
        given(mapper.toGeneratedNovedad(dto)).willReturn(novedad);

        ResponseEntity<List<Novedad>> response = novedadController.getNovedadesProfesor(date);

        assertThat(response.getBody()).containsExactly(novedad);
    }

    @Test
    void whenCreateNovedadProfesor_thenReturnsCreated() {
        Novedad body = new Novedad();
        NovedadDto dto = new NovedadDto();

        given(mapper.fromGeneratedNovedad(body)).willReturn(dto);
        given(novedadService.crearNovedadProfesor(dto)).willReturn(dto);
        given(mapper.toGeneratedNovedad(dto)).willReturn(body);

        ResponseEntity<Novedad> response = novedadController.createNovedadProfesor(body);

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(body);
    }

    @Test
    void whenCreateNovedadAlumno_thenReturnsCreated() {
        Novedad body = new Novedad();
        NovedadDto dto = new NovedadDto();

        given(mapper.fromGeneratedNovedad(body)).willReturn(dto);
        given(novedadService.crearNovedadAlumno(dto)).willReturn(dto);
        given(mapper.toGeneratedNovedad(dto)).willReturn(body);

        ResponseEntity<Novedad> response = novedadController.createNovedadAlumno(body);

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(body);
    }
}
