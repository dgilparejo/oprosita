package com.oprosita.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class ErrorDto {
    private String codigo;
    private String mensaje;
    private List<String> detalles;
}