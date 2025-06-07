package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.model.Alumno;
import com.oprosita.backend.util.MapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = MapperUtil.class)
public interface AlumnoMapper {

    // JPA ↔ DTO
    @Mapping(source = "grupo.id", target = "grupoId")
    @Mapping(source = "idKeycloak", target = "idKeycloak")
    AlumnoDto toAlumnoDto(Alumno alumno);

    @Mapping(source = "idKeycloak", target = "idKeycloak")
    Alumno toAlumnoEntity(AlumnoDto alumnoDto);

    List<AlumnoDto> toAlumnoDtoList(List<Alumno> alumnos);
    List<Alumno> toAlumnoEntityList(List<AlumnoDto> alumnosDto);

    // OpenAPI ↔ DTO
    com.oprosita.backend.model.generated.Alumno toGeneratedAlumno(AlumnoDto dto);
    AlumnoDto fromGeneratedAlumno(com.oprosita.backend.model.generated.Alumno generated);

    AlumnoDto fromGeneratedAlumno(com.oprosita.backend.model.generated.AddAlumnoToGrupoRequest request);
}
