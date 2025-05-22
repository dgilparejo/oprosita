package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.*;
import com.oprosita.backend.model.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeneralMapper {

    // Grupo
    GrupoDto toDto(Grupo grupo);
    Grupo toEntity(GrupoDto grupoDto);
    List<GrupoDto> toDtoList(List<Grupo> grupos);
    List<Grupo> toEntityList(List<GrupoDto> grupoDtos);

    // Mes
    MesDto toDto(Mes mes);
    Mes toEntity(MesDto mesDto);

    // Usuario
    UsuarioDto toDto(Usuario usuario);
    Usuario toEntity(UsuarioDto usuarioDto);

    // Alumno
    AlumnoDto toDto(Alumno alumno);
    Alumno toEntity(AlumnoDto alumnoDto);

    // Profesor
    ProfesorDto toDto(Profesor profesor);
    Profesor toEntity(ProfesorDto profesorDto);

    // ContenidoItem
    ContenidoItemDto toDto(ContenidoItem item);
    ContenidoItem toEntity(ContenidoItemDto dto);

    // Simulacro
    SimulacroDto toDto(Simulacro simulacro);
    Simulacro toEntity(SimulacroDto dto);

    // Novedad
    NovedadDto toDto(Novedad novedad);
    Novedad toEntity(NovedadDto dto);

    // Noticia
    NoticiaDto toDto(Noticia noticia);
    Noticia toEntity(NoticiaDto dto);

    // Archivo
    ArchivoDto toDto(Archivo archivo);
    Archivo toEntity(ArchivoDto dto);
}
