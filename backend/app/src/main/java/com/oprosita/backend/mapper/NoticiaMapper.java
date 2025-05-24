package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.NoticiaDto;
import com.oprosita.backend.model.Noticia;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoticiaMapper {

    NoticiaDto toNoticiaDto(Noticia noticia);
    Noticia toNoticiaEntity(NoticiaDto dto);
    List<NoticiaDto> toNoticiaDtoList(List<Noticia> noticias);
    List<Noticia> toNoticiaEntityList(List<NoticiaDto> dtos);
    com.oprosita.backend.model.generated.Noticia toNoticiaGenerated(NoticiaDto dto);
    NoticiaDto toNoticiaDto(com.oprosita.backend.model.generated.Noticia generated);
}
