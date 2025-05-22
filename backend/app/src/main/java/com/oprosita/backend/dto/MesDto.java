package com.oprosita.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MesDto {
    private Integer id;
    private String nombre;
    private Long grupoId;
}