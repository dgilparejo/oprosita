package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.model.Grupo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = { MesMapper.class })
public interface GrupoMapper {

    // JPA ↔ DTO
    GrupoDto toGrupoDto(Grupo grupo);
    Grupo toGrupoEntity(GrupoDto grupoDto);
    List<GrupoDto> toGrupoDtoList(List<Grupo> grupos);
    List<Grupo> toGrupoEntityList(List<GrupoDto> grupoDtos);

    // OpenAPI ↔ DTO
    @org.mapstruct.Mapping(source = "meses", target = "meses")
    GrupoDto fromGeneratedGrupo(com.oprosita.backend.model.generated.Grupo grupo);

    @org.mapstruct.Mapping(source = "meses", target = "meses")
    com.oprosita.backend.model.generated.Grupo toGeneratedGrupo(GrupoDto grupoDto);
}