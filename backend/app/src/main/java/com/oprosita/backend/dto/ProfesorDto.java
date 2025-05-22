package com.oprosita.backend.dto;

import com.oprosita.backend.model.generated.Profesor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfesorDto {
    private String value;
    private Profesor.TipoEnum tipo;
}