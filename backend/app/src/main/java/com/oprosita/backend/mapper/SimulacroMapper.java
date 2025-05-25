package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.SimulacroDto;
import com.oprosita.backend.model.Simulacro;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SimulacroMapper {

    // JPA ↔ DTO
    SimulacroDto toSimulacroDto(Simulacro simulacro);
    Simulacro toSimulacroEntity(SimulacroDto dto);
    List<SimulacroDto> toSimulacroDtoList(List<Simulacro> simulacros);
    List<Simulacro> toSimulacroEntityList(List<SimulacroDto> dtos);

    // OpenAPI ↔ DTO
    SimulacroDto fromGeneratedSimulacro(com.oprosita.backend.model.generated.Simulacro generated);
    com.oprosita.backend.model.generated.Simulacro toGeneratedSimulacro(SimulacroDto dto);
}