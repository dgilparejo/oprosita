package com.oprosita.backend.dto;

import lombok.Data;

@Data
public class ConversacionDto {
    private Long id;
    private Long usuarioId;
    private Long otroUsuarioId;
    private String ultimoMensaje;
    private String fechaUltimoMensaje;
    private Integer noLeidos;
}