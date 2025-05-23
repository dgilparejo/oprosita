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
    private String value;
    private TipoDestinatario tipo;
}