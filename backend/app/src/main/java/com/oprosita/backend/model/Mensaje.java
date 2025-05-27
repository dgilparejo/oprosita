package com.oprosita.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mensajes")
@Builder
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El remitente es obligatorio")
    @Column(nullable = false)
    private Long remitente;

    @NotNull(message = "El destinatario es obligatorio")
    @Column(nullable = false)
    private Long destinatario;

    @NotBlank(message = "El contenido no puede estar vacío")
    @Size(max = 1000, message = "El contenido no puede tener más de 1000 caracteres")
    @Column(nullable = false, length = 1000)
    private String contenido;

    @NotNull(message = "El estado de lectura es obligatorio")
    @Column(nullable = false)
    private Boolean leido;

    @NotNull(message = "La fecha de envío es obligatoria")
    @Column(name = "fecha_envio", nullable = false)
    private OffsetDateTime fechaEnvio;
}
