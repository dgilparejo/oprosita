package com.oprosita.backend.mapper;

import com.oprosita.backend.model.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeneralMapper {

    // Grupo
    com.oprosita.backend.model.generated.Grupo toDto(Grupo grupo);
    Grupo toEntity(com.oprosita.backend.model.generated.Grupo grupoDto);
    List<com.oprosita.backend.model.generated.Grupo> toDtoList(List<Grupo> grupos);
    List<Grupo> toEntityList(List<com.oprosita.backend.model.generated.Grupo> grupoDtos);

    // Mes
    com.oprosita.backend.model.generated.Mes toDto(Mes mes);
    Mes toEntity(com.oprosita.backend.model.generated.Mes mesDto);

    // Usuario
    com.oprosita.backend.model.generated.Usuario toDto(Usuario usuario);
    Usuario toEntity(com.oprosita.backend.model.generated.Usuario usuarioDto);

    // Alumno
    com.oprosita.backend.model.generated.Alumno toDto(Alumno alumno);
    Alumno toEntity(com.oprosita.backend.model.generated.Alumno alumnoDto);

    // Profesor
    com.oprosita.backend.model.generated.Profesor toDto(Profesor profesor);
    Profesor toEntity(com.oprosita.backend.model.generated.Profesor profesorDto);

    // ContenidoItem
    com.oprosita.backend.model.generated.ContenidoItem toDto(ContenidoItem item);
    ContenidoItem toEntity(com.oprosita.backend.model.generated.ContenidoItem dto);

    // Simulacro
    com.oprosita.backend.model.generated.Simulacro toDto(Simulacro simulacro);
    Simulacro toEntity(com.oprosita.backend.model.generated.Simulacro dto);

    // Novedad
    com.oprosita.backend.model.generated.Novedad toDto(Novedad novedad);
    Novedad toEntity(com.oprosita.backend.model.generated.Novedad dto);

    // Noticia
    com.oprosita.backend.model.generated.Noticia toDto(Noticia noticia);
    Noticia toEntity(com.oprosita.backend.model.generated.Noticia dto);

    // Archivo
    com.oprosita.backend.model.generated.Archivo toDto(Archivo archivo);
    Archivo toEntity(com.oprosita.backend.model.generated.Archivo dto);
}
