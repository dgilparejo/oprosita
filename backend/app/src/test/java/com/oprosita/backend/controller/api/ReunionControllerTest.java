package com.oprosita.backend.controller.api;

import com.oprosita.backend.dto.ReunionDto;
import com.oprosita.backend.mapper.ReunionMapper;
import com.oprosita.backend.model.generated.Reunion;
import com.oprosita.backend.service.ReunionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ReunionControllerTest {

    @Mock
    private ReunionService reunionService;

    @Mock
    private ReunionMapper mapper;

    @InjectMocks
    private ReunionController controller;

    @Test
    void givenGrupoId_whenGetReuniones_thenReturnsList() {
        // given
        Integer grupoId = 1;
        ReunionDto dto = new ReunionDto();
        Reunion reunion = new Reunion();

        given(reunionService.obtenerReunionesPorGrupo(grupoId.longValue())).willReturn(List.of(dto));
        given(mapper.toGeneratedReunion(dto)).willReturn(reunion);

        // when
        ResponseEntity<List<Reunion>> response = controller.getReunionesByGrupo(grupoId);

        // then
        assertThat(response.getBody()).containsExactly(reunion);
        then(reunionService).should().obtenerReunionesPorGrupo(grupoId.longValue());
    }

    @Test
    void givenGrupoIdAndDto_whenCreateReunion_thenReturnsCreated() {
        // given
        Integer grupoId = 1;
        Reunion input = new Reunion();
        ReunionDto dto = new ReunionDto();
        ReunionDto created = new ReunionDto();
        Reunion result = new Reunion();

        given(mapper.fromGeneratedReunion(input)).willReturn(dto);
        given(reunionService.crearReunionParaGrupo(grupoId.longValue(), dto)).willReturn(created);
        given(mapper.toGeneratedReunion(created)).willReturn(result);

        // when
        ResponseEntity<Reunion> response = controller.createReunion(grupoId, input);

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(result);
    }

    @Test
    void givenId_whenDeleteReunion_thenReturnsNoContent() {
        // given
        Integer id = 2;
        willDoNothing().given(reunionService).eliminar(id.longValue());

        // when
        ResponseEntity<Void> response = controller.deleteReunion(id);

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    void givenIdAndDto_whenUpdateReunion_thenReturnsUpdated() {
        // given
        Integer id = 2;
        Reunion input = new Reunion();
        ReunionDto dto = new ReunionDto();
        ReunionDto updated = new ReunionDto();
        Reunion output = new Reunion();

        given(mapper.fromGeneratedReunion(input)).willReturn(dto);
        given(reunionService.actualizar(id.longValue(), dto)).willReturn(updated);
        given(mapper.toGeneratedReunion(updated)).willReturn(output);

        // when
        ResponseEntity<Reunion> response = controller.updateReunion(id, input);

        // then
        assertThat(response.getBody()).isEqualTo(output);
    }
}
