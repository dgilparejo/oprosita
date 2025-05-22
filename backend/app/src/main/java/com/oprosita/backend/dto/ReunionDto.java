package com.oprosita.backend.dto;

import lombok.Data;

@Data
public class ReunionDto {
    private Long id;
    private String titulo;
    private String descripcion;
    private String fechaHora;
    private String enlace;
    private Long grupoId;
}