package com.oprosita.backend.controller.api;

import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.mapper.GrupoMapper;
import com.oprosita.backend.model.generated.Grupo;
import com.oprosita.backend.service.AlumnoService;
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
class AlumnoControllerTest {

    @Mock
    private AlumnoService alumnoService;

    @Mock
    private GrupoMapper grupoMapper;

    @InjectMocks
    private AlumnoController alumnoController;

    @Test
    void givenAlumnoId_whenDeleteAlumno_thenReturnsNoContent() {
        // given
        Integer alumnoId = 1;

        willDoNothing().given(alumnoService).eliminar(alumnoId.longValue());

        // when
        ResponseEntity<Void> response = alumnoController.deleteAlumno(alumnoId);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(204);
        then(alumnoService).should().eliminar(alumnoId.longValue());
    }

    @Test
    void givenAlumnoId_whenGetGrupos_thenReturnsGrupoList() {
        // given
        Integer alumnoId = 1;
        GrupoDto dto1 = new GrupoDto();
        GrupoDto dto2 = new GrupoDto();
        Grupo grupo1 = new Grupo();
        Grupo grupo2 = new Grupo();

        given(alumnoService.obtenerGrupoPorAlumno(alumnoId.longValue())).willReturn(List.of(dto1, dto2));
        given(grupoMapper.toGeneratedGrupo(dto1)).willReturn(grupo1);
        given(grupoMapper.toGeneratedGrupo(dto2)).willReturn(grupo2);

        // when
        ResponseEntity<List<Grupo>> response = alumnoController.getGruposByAlumno(alumnoId);

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).containsExactly(grupo1, grupo2);

        then(alumnoService).should().obtenerGrupoPorAlumno(alumnoId.longValue());
        then(grupoMapper).should(times(2)).toGeneratedGrupo(any(GrupoDto.class));
    }
}
