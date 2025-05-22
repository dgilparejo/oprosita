package com.oprosita.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulacroDto {
    private Integer id;
    private String descripcion;
    private Integer archivoId;
    private Integer grupoId;
}