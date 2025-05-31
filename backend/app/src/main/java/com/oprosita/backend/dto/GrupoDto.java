package com.oprosita.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class GrupoDto {
    @Null(message = "El ID debe generarse automáticamente")
    private Integer id;

    @NotBlank(message = "El nombre del grupo es obligatorio")
    private String nombre;

    @Valid
    @Size(min = 1, message = "Debe haber al menos un mes")
    private List<MesDto> meses;
}
