package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.model.ContenidoItem;
import com.oprosita.backend.util.MapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = MapperUtil.class)
public interface ContenidoItemMapper {

    // JPA → DTO
    @Mapping(source = "autor.id", target = "autorId")
    @Mapping(source = "mes.id", target = "mesId")
    @Mapping(source = "archivo.id", target = "archivoId")
    ContenidoItemDto toContenidoItemDto(ContenidoItem item);

    // DTO → JPA
    @Mapping(source = "mesId", target = "mes")
    @Mapping(source = "archivoId", target = "archivo")
    ContenidoItem toContenidoItemEntity(ContenidoItemDto dto);

    List<ContenidoItemDto> toContenidoItemDtoList(List<ContenidoItem> items);
    List<ContenidoItem> toContenidoItemEntityList(List<ContenidoItemDto> dtos);

    // OpenAPI ↔ DTO
    ContenidoItemDto fromGeneratedContenidoItem(com.oprosita.backend.model.generated.ContenidoItem generated);
    com.oprosita.backend.model.generated.ContenidoItem toGeneratedContenidoItem(ContenidoItemDto dto);
}
