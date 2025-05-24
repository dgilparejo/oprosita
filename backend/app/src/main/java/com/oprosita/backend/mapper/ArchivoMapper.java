package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.ArchivoDto;
import com.oprosita.backend.model.Archivo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArchivoMapper {

    ArchivoDto toArchivoDto(Archivo archivo);
    Archivo toArchivoEntity(ArchivoDto dto);
    List<ArchivoDto> toArchivoDtoList(List<Archivo> archivos);
    List<Archivo> toArchivoEntityList(List<ArchivoDto> dtos);
}
