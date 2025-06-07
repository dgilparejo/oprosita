package com.oprosita.backend.controller.api;

import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.mapper.GrupoMapper;
import com.oprosita.backend.model.generated.Grupo;
import com.oprosita.backend.service.ProfesorService;
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
class ProfesorControllerTest {

    @Mock
    private ProfesorService profesorService;

    @Mock
    private GrupoMapper grupoMapper;

    @InjectMocks
    private ProfesorController profesorController;

    @Test
    void givenProfesorId_whenGetGrupos_thenReturnsGrupoList() {
        Integer profesorId = 1;
        GrupoDto dto = new GrupoDto();
        Grupo grupo = new Grupo();

        given(profesorService.obtenerGruposPorProfesor(profesorId.longValue())).willReturn(List.of(dto));
        given(grupoMapper.toGeneratedGrupo(dto)).willReturn(grupo);

        ResponseEntity<List<Grupo>> response = profesorController.getGruposByProfesor(profesorId);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).containsExactly(grupo);
    }
}
