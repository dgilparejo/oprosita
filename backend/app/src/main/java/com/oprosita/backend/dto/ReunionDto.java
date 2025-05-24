package com.oprosita.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReunionDto {
    private Long id;
    private String titulo;
    private String descripcion;
    private String fechaHora;
    private String enlace;
    private Long grupoId;
}