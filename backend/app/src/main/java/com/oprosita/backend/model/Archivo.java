package com.oprosita.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.AssertTrue;
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

    /**
     * Reglas:
     * - Al menos uno (url o datos) debe estar presente
     * - No pueden estar ambos al mismo tiempo
     */
    @AssertTrue(message = "Debe tener una URL o datos binarios, pero no ambos")
    public boolean isContenidoValido() {
        boolean tieneUrl = url != null && !url.isBlank();
        boolean tieneDatos = datos != null && datos.length > 0;
        return tieneUrl ^ tieneDatos; // XOR: uno u otro, no ambos
    }
}
