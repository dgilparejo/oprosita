package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.model.ContenidoItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContenidoItemMapper {

    ContenidoItemDto toContenidoItemDto(ContenidoItem item);
    ContenidoItem toContenidoItemEntity(ContenidoItemDto dto);
    List<ContenidoItemDto> toContenidoItemDtoList(List<ContenidoItem> items);
    List<ContenidoItem> toContenidoItemEntityList(List<ContenidoItemDto> dtos);
    ContenidoItem toContenidoItemEntity(com.oprosita.backend.model.generated.ContenidoItem generatedItem);
    com.oprosita.backend.model.generated.ContenidoItem toContenidoItemGenerated(ContenidoItemDto dto);
    ContenidoItemDto toContenidoItemDto(com.oprosita.backend.model.generated.ContenidoItem generated);
}
