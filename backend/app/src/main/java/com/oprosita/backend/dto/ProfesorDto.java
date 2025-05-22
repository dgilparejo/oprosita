package com.oprosita.backend.dto;

import com.oprosita.backend.model.TipoDestinatario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfesorDto {
    private String value;
    private TipoDestinatario tipo;
}