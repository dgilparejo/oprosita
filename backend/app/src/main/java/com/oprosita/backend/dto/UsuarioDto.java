package com.oprosita.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {
    private Integer id;
    private String nombre;
    private Integer grupoId;
}