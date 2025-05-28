package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.ReunionDto;
import com.oprosita.backend.model.Reunion;
import com.oprosita.backend.util.MapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MapperUtil.class})
public interface ReunionMapper {

    // JPA ↔ DTO
    @Mapping(source = "grupo.id", target = "grupoId")
    ReunionDto toReunionDto(Reunion reunion);
    Reunion toReunionEntity(ReunionDto reunionDto);
    List<ReunionDto> toReunionDtoList(List<Reunion> reuniones);
    List<Reunion> toReunionEntityList(List<ReunionDto> reunionesDto);

    // OpenAPI ↔ DTO
    ReunionDto fromGeneratedReunion(com.oprosita.backend.model.generated.Reunion generated);
    com.oprosita.backend.model.generated.Reunion toGeneratedReunion(ReunionDto dto);
}
