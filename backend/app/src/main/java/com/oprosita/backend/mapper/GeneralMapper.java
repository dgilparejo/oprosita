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
    List<MesDto> toDtoList(List<Mes> meses);
    List<Mes> toEntityList(List<MesDto> mesesDto);

    // Usuario
    UsuarioDto toDto(Usuario usuario);
    Usuario toEntity(UsuarioDto usuarioDto);
    List<UsuarioDto> toDtoList(List<Usuario> usuarios);
    List<Usuario> toEntityList(List<UsuarioDto> usuariosDto);

    // Alumno
    AlumnoDto toDto(Alumno alumno);
    Alumno toEntity(AlumnoDto alumnoDto);
    List<AlumnoDto> toDtoList(List<Alumno> alumnos);
    List<Alumno> toEntityList(List<AlumnoDto> alumnosDto);

    // Profesor
    ProfesorDto toDto(Profesor profesor);
    Profesor toEntity(ProfesorDto profesorDto);
    List<ProfesorDto> toDtoList(List<Profesor> profesores);
    List<Profesor> toEntityList(List<ProfesorDto> profesoresDto);

    // ContenidoItem
    ContenidoItemDto toDto(ContenidoItem item);
    ContenidoItem toEntity(ContenidoItemDto dto);
    List<ContenidoItemDto> toDtoList(List<ContenidoItem> items);
    List<ContenidoItem> toEntityList(List<ContenidoItemDto> dtos);

    // Simulacro
    SimulacroDto toDto(Simulacro simulacro);
    Simulacro toEntity(SimulacroDto dto);
    List<SimulacroDto> toDtoList(List<Simulacro> simulacros);
    List<Simulacro> toEntityList(List<SimulacroDto> dtos);

    // Novedad
    NovedadDto toDto(Novedad novedad);
    Novedad toEntity(NovedadDto dto);
    List<NovedadDto> toDtoList(List<Novedad> novedades);
    List<Novedad> toEntityList(List<NovedadDto> dtos);

    // Noticia
    NoticiaDto toDto(Noticia noticia);
    Noticia toEntity(NoticiaDto dto);
    List<NoticiaDto> toDtoList(List<Noticia> noticias);
    List<Noticia> toEntityList(List<NoticiaDto> dtos);

    // Archivo
    ArchivoDto toDto(Archivo archivo);
    Archivo toEntity(ArchivoDto dto);
    List<ArchivoDto> toDtoList(List<Archivo> archivos);
    List<Archivo> toEntityList(List<ArchivoDto> dtos);

    // Conversacion
    ConversacionDto toDto(Conversacion conversacion);
    Conversacion toEntity(ConversacionDto dto);
    List<ConversacionDto> toDtoList(List<Conversacion> conversaciones);
    List<Conversacion> toEntityList(List<ConversacionDto> dtos);

    // Mensaje
    MensajeDto toDto(Mensaje mensaje);
    Mensaje toEntity(MensajeDto dto);
    List<MensajeDto> toDtoList(List<Mensaje> mensajes);
    List<Mensaje> toEntityList(List<MensajeDto> dtos);
}
