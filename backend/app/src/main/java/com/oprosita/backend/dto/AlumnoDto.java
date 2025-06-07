package com.oprosita.backend.dto;

import com.oprosita.backend.model.TipoDestinatario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlumnoDto {

    @Null(message = "El ID debe generarse automáticamente")
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;

    @Positive(message = "El grupoId debe ser un número positivo")
    private Integer grupoId;

    @NotNull(message = "El tipo es obligatorio")
    private TipoDestinatario tipo;

    private String idKeycloak;
}
