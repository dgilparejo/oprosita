package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.NovedadDto;
import com.oprosita.backend.model.Novedad;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NovedadMapper {

    NovedadDto toNovedadDto(Novedad novedad);
    Novedad toNovedadEntity(NovedadDto dto);
    List<NovedadDto> toNovedadDtoList(List<Novedad> novedades);
    List<Novedad> toNovedadEntityList(List<NovedadDto> dtos);
    com.oprosita.backend.model.generated.Novedad toNovedadGenerated(NovedadDto dto);
    NovedadDto toNovedadDto(com.oprosita.backend.model.generated.Novedad generated);
}
