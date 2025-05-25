package com.oprosita.backend.dto;

import com.oprosita.backend.model.TipoDestinatario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfesorDto {
    private Integer id;
    private String nombre;
    private Integer grupoId;
    private TipoDestinatario tipo;
}