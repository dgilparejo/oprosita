package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.MesDto;
import com.oprosita.backend.model.Mes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MesMapper {

    // JPA ↔ DTO
    MesDto toMesDto(Mes mes);
    Mes toMesEntity(MesDto mesDto);
    List<MesDto> toMesDtoList(List<Mes> meses);
    List<Mes> toMesEntityList(List<MesDto> mesesDto);

    // OpenAPI ↔ DTO
    MesDto fromGeneratedMes(com.oprosita.backend.model.generated.Mes generated);
    com.oprosita.backend.model.generated.Mes toGeneratedMes(MesDto dto);
}