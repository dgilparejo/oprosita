package com.oprosita.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MesDto {
    private Integer id;
    private String nombre;
    private Long grupoId;
}