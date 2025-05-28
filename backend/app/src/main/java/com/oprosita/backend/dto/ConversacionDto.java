package com.oprosita.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConversacionDto {
    @Null(message = "El ID debe generarse automáticamente")
    private Integer id;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El ID del otro usuario es obligatorio")
    private Integer otroUsuarioId;

    @NotBlank(message = "El último mensaje no puede estar vacío")
    private String ultimoMensaje;

    @NotNull(message = "La fecha del último mensaje es obligatoria")
    private OffsetDateTime fechaUltimoMensaje;

    @Min(value = 0, message = "El número de mensajes no leídos no puede ser negativo")
    private Integer noLeidos;
}
