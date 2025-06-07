package com.oprosita.backend.dto;

import com.oprosita.backend.model.TipoDestinatario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfesorDto {
    @Null(message = "El ID debe generarse automáticamente")
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private List<@NotNull Integer> grupoIds;

    @NotNull(message = "El tipo es obligatorio")
    private TipoDestinatario tipo;

    private String idKeycloak;
}
