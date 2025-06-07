package com.oprosita.backend.controller.api;

import com.oprosita.backend.dto.SimulacroDto;
import com.oprosita.backend.mapper.SimulacroMapper;
import com.oprosita.backend.model.generated.Simulacro;
import com.oprosita.backend.service.SimulacroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class SimulacroControllerTest {

    @Mock
    private SimulacroService simulacroService;

    @Mock
    private SimulacroMapper mapper;

    @InjectMocks
    private SimulacroController controller;

    @Test
    void whenGetSimulacros_thenReturnsList() {
        // given
        SimulacroDto dto = new SimulacroDto();
        Simulacro simulacro = new Simulacro();

        given(simulacroService.obtenerTodos()).willReturn(List.of(dto));
        given(mapper.toGeneratedSimulacro(dto)).willReturn(simulacro);

        // when
        ResponseEntity<List<Simulacro>> response = controller.getSimulacros();

        // then
        assertThat(response.getBody()).containsExactly(simulacro);
        then(simulacroService).should().obtenerTodos();
    }

    @Test
    void whenCreateSimulacro_thenReturnsCreated() {
        // given
        String descripcion = "Simulacro prueba";
        MultipartFile file = mock(MultipartFile.class);
        SimulacroDto dto = new SimulacroDto();
        Simulacro simulacro = new Simulacro();

        given(simulacroService.crearSimulacro(descripcion, file)).willReturn(dto);
        given(mapper.toGeneratedSimulacro(dto)).willReturn(simulacro);

        // when
        ResponseEntity<Simulacro> response = controller.createSimulacro(descripcion, file);

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(simulacro);
    }

    @Test
    void whenDeleteSimulacro_thenReturnsNoContent() {
        // given
        Integer id = 3;
        willDoNothing().given(simulacroService).eliminar(id.longValue());

        // when
        ResponseEntity<Void> response = controller.deleteSimulacro(id);

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        then(simulacroService).should().eliminar(id.longValue());
    }
}
