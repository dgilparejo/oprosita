package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.model.ContenidoItem;
import com.oprosita.backend.util.MapperUtil;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MapperUtil.class})
public interface ContenidoItemMapper {

    // JPA ↔ DTO
    ContenidoItemDto toContenidoItemDto(ContenidoItem item);
    ContenidoItem toContenidoItemEntity(ContenidoItemDto dto);
    List<ContenidoItemDto> toContenidoItemDtoList(List<ContenidoItem> items);
    List<ContenidoItem> toContenidoItemEntityList(List<ContenidoItemDto> dtos);

    // OpenAPI ↔ DTO
    ContenidoItemDto fromGeneratedContenidoItem(com.oprosita.backend.model.generated.ContenidoItem generated);
    com.oprosita.backend.model.generated.ContenidoItem toGeneratedContenidoItem(ContenidoItemDto dto);
}
