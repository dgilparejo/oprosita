package com.oprosita.backend.dto;

import com.oprosita.backend.model.TipoContenido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContenidoItemDto {
    @Null(message = "El ID debe generarse automáticamente")
    private Integer id;

    @NotBlank(message = "El texto del contenido no puede estar vacío")
    private String texto;

    @NotNull(message = "El tipo de contenido es obligatorio")
    private TipoContenido tipoContenido;

    @NotNull(message = "El autor del contenido es obligatorio")
    private Integer autorId;

    private Integer mesId;

    private Integer archivoId;
}
