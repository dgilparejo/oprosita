package com.oprosita.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del archivo es obligatorio")
    private String nombre;

    @NotBlank(message = "El tipo del archivo es obligatorio")
    private String tipo;

    private String url;

    @Lob
    private byte[] datos;
}
