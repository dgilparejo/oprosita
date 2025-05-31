package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.MensajeDto;
import com.oprosita.backend.model.Mensaje;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MensajeMapper {

    // JPA ↔ DTO
    MensajeDto toMensajeDto(Mensaje mensaje);
    Mensaje toMensajeEntity(MensajeDto dto);
    List<MensajeDto> toMensajeDtoList(List<Mensaje> mensajes);
    List<Mensaje> toMensajeEntityList(List<MensajeDto> dtos);

    // OpenAPI ↔ DTO
    com.oprosita.backend.model.generated.Mensaje toGeneratedMensaje(MensajeDto dto);
    MensajeDto fromGeneratedMensaje(com.oprosita.backend.model.generated.Mensaje generated);
}
