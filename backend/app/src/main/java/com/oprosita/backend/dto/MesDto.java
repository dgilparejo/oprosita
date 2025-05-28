package com.oprosita.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MesDto {
    @Null(message = "El ID debe generarse automáticamente")
    private Integer id;

    @NotBlank(message = "El nombre del mes es obligatorio")
    private String nombre;

    @NotNull(message = "El grupoId es obligatorio")
    private Long grupoId;
}
