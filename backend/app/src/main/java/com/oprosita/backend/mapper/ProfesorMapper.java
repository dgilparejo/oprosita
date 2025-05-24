package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.ProfesorDto;
import com.oprosita.backend.model.Profesor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfesorMapper {

    ProfesorDto toProfesorDto(Profesor profesor);
    Profesor toProfesorEntity(ProfesorDto profesorDto);
    List<ProfesorDto> toProfesorDtoList(List<Profesor> profesores);
    List<Profesor> toProfesorEntityList(List<ProfesorDto> profesoresDto);
}
