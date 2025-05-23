package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.ConversacionDto;
import com.oprosita.backend.model.Conversacion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConversacionMapper {

    ConversacionDto toConversacionDto(Conversacion conversacion);
    Conversacion toConversacionEntity(ConversacionDto dto);
    List<ConversacionDto> toConversacionDtoList(List<Conversacion> conversaciones);
    List<Conversacion> toConversacionEntityList(List<ConversacionDto> dtos);
    com.oprosita.backend.model.generated.Conversacion toConversacionGenerated(ConversacionDto dto);
    ConversacionDto toConversacionDto(com.oprosita.backend.model.generated.Conversacion generated);
}
