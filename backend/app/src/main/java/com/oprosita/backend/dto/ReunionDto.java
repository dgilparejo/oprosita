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
public class ReunionDto {
    @Null(message = "El ID debe generarse automáticamente")
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String descripcion;

    @NotNull(message = "La fecha y hora es obligatoria")
    private OffsetDateTime fechaHora;

    private String enlace;

    @NotNull(message = "El grupoId es obligatorio")
    private Long grupoId;
}
