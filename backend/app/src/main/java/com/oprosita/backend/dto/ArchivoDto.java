package com.oprosita.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchivoDto {
    private Integer id;
    private String nombre;
    private String tipo;
    private String url;
    private byte[] datos;
}