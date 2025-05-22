package com.oprosita.backend.dto;

import com.oprosita.backend.model.generated.Novedad;
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
    private Novedad.TipoDestinatarioEnum tipoDestinatario;
    private OffsetDateTime fechaCreacion;
}