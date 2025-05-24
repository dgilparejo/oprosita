package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.model.Alumno;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlumnoMapper {

    AlumnoDto toAlumnoDto(Alumno alumno);
    Alumno toAlumnoEntity(AlumnoDto alumnoDto);
    List<AlumnoDto> toAlumnoDtoList(List<Alumno> alumnos);
    List<Alumno> toAlumnoEntityList(List<AlumnoDto> alumnosDto);
    com.oprosita.backend.model.generated.Alumno toAlumnoGenerated(AlumnoDto dto);
    AlumnoDto toAlumnoDto(com.oprosita.backend.model.generated.Alumno generated);
}
