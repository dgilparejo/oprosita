package com.oprosita.backend.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class MensajeDto {
    private Long id;
    private Long remitente;
    private Long destinatario;
    private String contenido;
    private Boolean leido;
    private OffsetDateTime fechaEnvio;
}