package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.ReunionDto;
import com.oprosita.backend.model.Reunion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReunionMapper {

    ReunionDto toReunionDto(Reunion reunion);
    Reunion toReunionEntity(ReunionDto reunionDto);
    List<ReunionDto> toReunionDtoList(List<Reunion> reuniones);
    List<Reunion> toReunionEntityList(List<ReunionDto> reunionesDto);
    com.oprosita.backend.model.generated.Reunion toReunionGenerated(ReunionDto dto);
    ReunionDto toReunionDto(com.oprosita.backend.model.generated.Reunion generated);
}
