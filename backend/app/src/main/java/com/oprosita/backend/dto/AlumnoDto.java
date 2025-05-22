package com.oprosita.backend.dto;

import com.oprosita.backend.model.generated.Alumno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoDto {
    private String value;
    private Alumno.TipoEnum tipo;
}