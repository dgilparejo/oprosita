package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.NovedadDto;
import com.oprosita.backend.model.Novedad;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NovedadMapper {
    // JPA ↔ DTO
    NovedadDto toNovedadDto(Novedad novedad);
    Novedad toNovedadEntity(NovedadDto dto);
    List<NovedadDto> toNovedadDtoList(List<Novedad> novedades);
    List<Novedad> toNovedadEntityList(List<NovedadDto> dtos);

    // OpenAPI ↔ DTO
    NovedadDto fromGeneratedNovedad(com.oprosita.backend.model.generated.Novedad generated);
    com.oprosita.backend.model.generated.Novedad toGeneratedNovedad(NovedadDto dto);
}
