package com.oprosita.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MensajeDto {
    @Null(message = "El ID debe generarse automáticamente")
    private Long id;

    @NotNull(message = "El remitente es obligatorio")
    private Long remitente;

    @NotNull(message = "El destinatario es obligatorio")
    private Long destinatario;

    @NotBlank(message = "El contenido del mensaje no puede estar vacío")
    private String contenido;

    private Boolean leido;

    private OffsetDateTime fechaEnvio;
}
