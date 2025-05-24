package com.oprosita.backend.model;

import jakarta.persistence.Entity;
import lombok.*;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private Long remitente;

    @Column(nullable = false)
    private Long destinatario;

    @Column(nullable = false, length = 1000)
    private String contenido;

    @Column(nullable = false)
    private Boolean leido;

    @Column(name = "fecha_envio", nullable = false)
    private OffsetDateTime fechaEnvio;
}