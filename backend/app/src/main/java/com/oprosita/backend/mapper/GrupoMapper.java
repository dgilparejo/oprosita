package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.model.Grupo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GrupoMapper {

    GrupoDto toGrupoDto(Grupo grupo);
    Grupo toGrupoEntity(GrupoDto grupoDto);
    List<GrupoDto> toGrupoDtoList(List<Grupo> grupos);
    List<Grupo> toGrupoEntityList(List<GrupoDto> grupoDtos);
    com.oprosita.backend.model.generated.Grupo toGrupoGenerated(GrupoDto dto);
    GrupoDto toGrupoDto(com.oprosita.backend.model.generated.Grupo generated);
}
