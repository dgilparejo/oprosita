package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.MensajeDto;
import com.oprosita.backend.model.Mensaje;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MensajeMapper {

    MensajeDto toMensajeDto(Mensaje mensaje);
    Mensaje toMensajeEntity(MensajeDto dto);
    List<MensajeDto> toMensajeDtoList(List<Mensaje> mensajes);
    List<Mensaje> toMensajeEntityList(List<MensajeDto> dtos);
    com.oprosita.backend.model.generated.Mensaje toMensajeGenerated(MensajeDto dto);
    MensajeDto toMensajeDto(com.oprosita.backend.model.generated.Mensaje generated);
}
