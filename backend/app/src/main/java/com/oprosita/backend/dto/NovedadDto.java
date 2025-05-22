package com.oprosita.backend.dto;

import com.oprosita.backend.model.TipoDestinatario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NovedadDto {
    private Integer id;
    private String texto;
    private String value;
    private TipoDestinatario tipoDestinatario;
    private OffsetDateTime fechaCreacion;
}