package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.ProfesorDto;
import com.oprosita.backend.model.Profesor;
import com.oprosita.backend.util.MapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = MapperUtil.class)
public interface ProfesorMapper {

    // JPA ↔ DTO
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "grupo.id", target = "grupoId")
    @Mapping(source = "tipo", target = "tipo", qualifiedByName = "toEntityTipoDestinatario")
    ProfesorDto toProfesorDto(Profesor profesor);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "grupoId", target = "grupo.id")
    @Mapping(source = "tipo", target = "tipo", qualifiedByName = "toGeneratedProfesorTipoEnum")
    Profesor toProfesorEntity(ProfesorDto profesorDto);

    List<ProfesorDto> toProfesorDtoList(List<Profesor> profesores);
    List<Profesor> toProfesorEntityList(List<ProfesorDto> profesoresDto);

    // OpenAPI ↔ DTO
    ProfesorDto fromGeneratedProfesor(com.oprosita.backend.model.generated.Profesor generated);
    com.oprosita.backend.model.generated.Profesor toGeneratedProfesor(ProfesorDto dto);
}
