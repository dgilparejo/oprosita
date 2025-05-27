package com.oprosita.backend.dto;

import com.oprosita.backend.model.TipoDestinatario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfesorDto {

    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotEmpty(message = "Debe tener al menos un grupo asignado")
    private List<@NotNull Integer> grupoIds;

    @NotNull(message = "El tipo es obligatorio")
    private TipoDestinatario tipo;
}
