package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.NoticiaDto;
import com.oprosita.backend.model.Noticia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoticiaMapper {
    // JPA ↔ DTO
    @Mapping(source = "archivo.id", target = "archivoId")
    NoticiaDto toNoticiaDto(Noticia noticia);
    Noticia toNoticiaEntity(NoticiaDto dto);
    List<NoticiaDto> toNoticiaDtoList(List<Noticia> noticias);
    List<Noticia> toNoticiaEntityList(List<NoticiaDto> dtos);

    // OpenAPI ↔ DTO
    NoticiaDto fromGeneratedNoticia(com.oprosita.backend.model.generated.Noticia generated);
    com.oprosita.backend.model.generated.Noticia toGeneratedNoticia(NoticiaDto dto);
}