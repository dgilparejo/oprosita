package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.*;
import com.oprosita.backend.model.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeneralMapper {

    // Grupo
    GrupoDto toGrupoDto(Grupo grupo);
    Grupo toGrupoEntity(GrupoDto grupoDto);
    List<GrupoDto> toGrupoDtoList(List<Grupo> grupos);
    List<Grupo> toGrupoEntityList(List<GrupoDto> grupoDtos);

    // Mes
    MesDto toMesDto(Mes mes);
    Mes toMesEntity(MesDto mesDto);
    List<MesDto> toMesDtoList(List<Mes> meses);
    List<Mes> toMesEntityList(List<MesDto> mesesDto);

    // Reunion
    ReunionDto toReunionDto(Reunion reunion);
    Reunion toReunionEntity(ReunionDto reunionDto);
    List<ReunionDto> toReunionDtoList(List<Reunion> reuniones);
    List<Reunion> toReunionEntityList(List<ReunionDto> reunionesDto);

    // Usuario
    UsuarioDto toUsuarioDto(Usuario usuario);
    Usuario toUsuarioEntity(UsuarioDto usuarioDto);
    List<UsuarioDto> toUsuarioDtoList(List<Usuario> usuarios);
    List<Usuario> toUsuarioEntityList(List<UsuarioDto> usuariosDto);

    // Alumno
    AlumnoDto toAlumnoDto(Alumno alumno);
    Alumno toAlumnoEntity(AlumnoDto alumnoDto);
    List<AlumnoDto> toAlumnoDtoList(List<Alumno> alumnos);
    List<Alumno> toAlumnoEntityList(List<AlumnoDto> alumnosDto);

    // Profesor
    ProfesorDto toProfesorDto(Profesor profesor);
    Profesor toProfesorEntity(ProfesorDto profesorDto);
    List<ProfesorDto> toProfesorDtoList(List<Profesor> profesores);
    List<Profesor> toProfesorEntityList(List<ProfesorDto> profesoresDto);

    // ContenidoItem
    ContenidoItemDto toContenidoItemDto(ContenidoItem item);
    ContenidoItem toContenidoItemEntity(ContenidoItemDto dto);
    List<ContenidoItemDto> toContenidoItemDtoList(List<ContenidoItem> items);
    List<ContenidoItem> toContenidoItemEntityList(List<ContenidoItemDto> dtos);
    ContenidoItem toContenidoItemEntity(com.oprosita.backend.model.generated.ContenidoItem generatedItem);

    // Simulacro
    SimulacroDto toSimulacroDto(Simulacro simulacro);
    Simulacro toSimulacroEntity(SimulacroDto dto);
    List<SimulacroDto> toSimulacroDtoList(List<Simulacro> simulacros);
    List<Simulacro> toSimulacroEntityList(List<SimulacroDto> dtos);

    // Novedad
    NovedadDto toNovedadDto(Novedad novedad);
    Novedad toNovedadEntity(NovedadDto dto);
    List<NovedadDto> toNovedadDtoList(List<Novedad> novedades);
    List<Novedad> toNovedadEntityList(List<NovedadDto> dtos);

    // Noticia
    NoticiaDto toNoticiaDto(Noticia noticia);
    Noticia toNoticiaEntity(NoticiaDto dto);
    List<NoticiaDto> toNoticiaDtoList(List<Noticia> noticias);
    List<Noticia> toNoticiaEntityList(List<NoticiaDto> dtos);

    // Archivo
    ArchivoDto toArchivoDto(Archivo archivo);
    Archivo toArchivoEntity(ArchivoDto dto);
    List<ArchivoDto> toArchivoDtoList(List<Archivo> archivos);
    List<Archivo> toArchivoEntityList(List<ArchivoDto> dtos);

    // Conversacion
    ConversacionDto toConversacionDto(Conversacion conversacion);
    Conversacion toConversacionEntity(ConversacionDto dto);
    List<ConversacionDto> toConversacionDtoList(List<Conversacion> conversaciones);
    List<Conversacion> toConversacionEntityList(List<ConversacionDto> dtos);

    // Mensaje
    MensajeDto toMensajeDto(Mensaje mensaje);
    Mensaje toMensajeEntity(MensajeDto dto);
    List<MensajeDto> toMensajeDtoList(List<Mensaje> mensajes);
    List<Mensaje> toMensajeEntityList(List<MensajeDto> dtos);
}