package com.oprosita.backend.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Error {
    private String codigo;
    private String mensaje;
    private List<String> detalles;
}