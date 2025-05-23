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
    com.oprosita.backend.model.generated.Grupo toGrupoGenerated(GrupoDto dto);
    GrupoDto toGrupoDto(com.oprosita.backend.model.generated.Grupo generated);

    // Mes
    MesDto toMesDto(Mes mes);
    Mes toMesEntity(MesDto mesDto);
    List<MesDto> toMesDtoList(List<Mes> meses);
    List<Mes> toMesEntityList(List<MesDto> mesesDto);
    com.oprosita.backend.model.generated.Mes toMesGenerated(MesDto dto);
    MesDto toMesDto(com.oprosita.backend.model.generated.Mes generated);

    // Reunion
    ReunionDto toReunionDto(Reunion reunion);
    Reunion toReunionEntity(ReunionDto reunionDto);
    List<ReunionDto> toReunionDtoList(List<Reunion> reuniones);
    List<Reunion> toReunionEntityList(List<ReunionDto> reunionesDto);
    com.oprosita.backend.model.generated.Reunion toReunionGenerated(ReunionDto dto);
    ReunionDto toReunionDto(com.oprosita.backend.model.generated.Reunion generated);

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
    com.oprosita.backend.model.generated.Alumno toAlumnoGenerated(AlumnoDto dto);
    AlumnoDto toAlumnoDto(com.oprosita.backend.model.generated.Alumno generated);

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
    com.oprosita.backend.model.generated.ContenidoItem toContenidoItemGenerated(ContenidoItemDto dto);
    ContenidoItemDto toContenidoItemDto(com.oprosita.backend.model.generated.ContenidoItem generated);

    // Simulacro
    SimulacroDto toSimulacroDto(Simulacro simulacro);
    Simulacro toSimulacroEntity(SimulacroDto dto);
    List<SimulacroDto> toSimulacroDtoList(List<Simulacro> simulacros);
    List<Simulacro> toSimulacroEntityList(List<SimulacroDto> dtos);
    com.oprosita.backend.model.generated.Simulacro toSimulacroGenerated(SimulacroDto dto);
    SimulacroDto toSimulacroDto(com.oprosita.backend.model.generated.Simulacro generated);

    // Novedad
    NovedadDto toNovedadDto(Novedad novedad);
    Novedad toNovedadEntity(NovedadDto dto);
    List<NovedadDto> toNovedadDtoList(List<Novedad> novedades);
    List<Novedad> toNovedadEntityList(List<NovedadDto> dtos);
    com.oprosita.backend.model.generated.Novedad toNovedadGenerated(NovedadDto dto);
    NovedadDto toNovedadDto(com.oprosita.backend.model.generated.Novedad generated);

    // Noticia
    NoticiaDto toNoticiaDto(Noticia noticia);
    Noticia toNoticiaEntity(NoticiaDto dto);
    List<NoticiaDto> toNoticiaDtoList(List<Noticia> noticias);
    List<Noticia> toNoticiaEntityList(List<NoticiaDto> dtos);
    com.oprosita.backend.model.generated.Noticia toNoticiaGenerated(NoticiaDto dto);
    NoticiaDto toNoticiaDto(com.oprosita.backend.model.generated.Noticia generated);

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
    com.oprosita.backend.model.generated.Conversacion toConversacionGenerated(ConversacionDto dto);
    ConversacionDto toConversacionDto(com.oprosita.backend.model.generated.Conversacion generated);

    // Mensaje
    MensajeDto toMensajeDto(Mensaje mensaje);
    Mensaje toMensajeEntity(MensajeDto dto);
    List<MensajeDto> toMensajeDtoList(List<Mensaje> mensajes);
    List<Mensaje> toMensajeEntityList(List<MensajeDto> dtos);
    com.oprosita.backend.model.generated.Mensaje toMensajeGenerated(MensajeDto dto);
    MensajeDto toMensajeDto(com.oprosita.backend.model.generated.Mensaje generated);
}