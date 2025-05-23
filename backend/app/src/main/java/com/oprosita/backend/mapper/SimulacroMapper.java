package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.SimulacroDto;
import com.oprosita.backend.model.Simulacro;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SimulacroMapper {

    SimulacroDto toSimulacroDto(Simulacro simulacro);
    Simulacro toSimulacroEntity(SimulacroDto dto);
    List<SimulacroDto> toSimulacroDtoList(List<Simulacro> simulacros);
    List<Simulacro> toSimulacroEntityList(List<SimulacroDto> dtos);
    com.oprosita.backend.model.generated.Simulacro toSimulacroGenerated(SimulacroDto dto);
    SimulacroDto toSimulacroDto(com.oprosita.backend.model.generated.Simulacro generated);
}
