package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.ConversacionDto;
import com.oprosita.backend.model.Conversacion;
import com.oprosita.backend.util.MapperUtil;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MapperUtil.class})
public interface ConversacionMapper {

    // JPA ↔ DTO
    ConversacionDto toConversacionDto(Conversacion conversacion);
    Conversacion toConversacionEntity(ConversacionDto dto);
    List<ConversacionDto> toConversacionDtoList(List<Conversacion> conversaciones);
    List<Conversacion> toConversacionEntityList(List<ConversacionDto> dtos);

    // OpenAPI ↔ DTO
    ConversacionDto fromGeneratedConversacion(com.oprosita.backend.model.generated.Conversacion generated);
    com.oprosita.backend.model.generated.Conversacion toGeneratedConversacion(ConversacionDto dto);
}
