package com.oprosita.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConversacionDto {
    private Long id;
    private Long usuarioId;
    private Long otroUsuarioId;
    private MensajeDto ultimoMensaje;
    private String fechaUltimoMensaje;
    private Integer noLeidos;
}