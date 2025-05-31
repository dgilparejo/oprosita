package com.oprosita.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticiaDto {
    @Null(message = "El ID debe generarse automáticamente")
    private Integer id;

    private String descripcion;

    private Integer archivoId;
}
