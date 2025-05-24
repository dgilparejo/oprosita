package com.oprosita.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MensajeDto {
    private Long id;
    private Long remitente;
    private Long destinatario;
    private String contenido;
    private Boolean leido;
    private OffsetDateTime fechaEnvio;
}