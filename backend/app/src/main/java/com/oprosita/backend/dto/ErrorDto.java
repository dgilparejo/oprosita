package com.oprosita.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
    private String codigo;
    private String mensaje;
    private List<String> detalles;
}