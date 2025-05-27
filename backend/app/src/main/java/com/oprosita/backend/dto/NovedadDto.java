package com.oprosita.backend.dto;

import com.oprosita.backend.model.TipoDestinatario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NovedadDto {

    private Integer id;

    @NotBlank(message = "El texto es obligatorio")
    private String texto;

    @NotNull(message = "El tipo de destinatario es obligatorio")
    private TipoDestinatario tipoDestinatario;

    @NotNull(message = "La fecha de creación es obligatoria")
    private OffsetDateTime fechaCreacion;
}
