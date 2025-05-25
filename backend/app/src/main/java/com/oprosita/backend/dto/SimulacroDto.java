package com.oprosita.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimulacroDto {
    private Integer id;
    private String descripcion;
    private Integer archivoId;
}