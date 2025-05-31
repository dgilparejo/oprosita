package com.oprosita.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArchivoDto {
    @Null(message = "El ID debe generarse automáticamente")
    private Integer id;

    @NotBlank(message = "El nombre del archivo no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El tipo MIME no puede estar vacío")
    private String tipo;

    @NotBlank(message = "La URL no puede estar vacía")
    private String url;

    @NotNull(message = "Los datos del archivo no pueden ser nulos")
    private byte[] datos;
}
