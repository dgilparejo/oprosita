package com.oprosita.backend.dto;

import com.oprosita.backend.model.Mes;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GrupoDto {
    private Integer id;
    private String nombre;
    private List<@Valid Mes> meses = new ArrayList<>();
}