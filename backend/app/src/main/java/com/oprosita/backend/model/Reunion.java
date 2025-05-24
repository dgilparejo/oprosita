package com.oprosita.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reuniones")
public class Reunion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 1000)
    private String descripcion;

    @Column(name = "fecha_hora", nullable = false)
    private OffsetDateTime fechaHora;

    @Column(nullable = false)
    private String enlace;

    @Column(name = "grupo_id", nullable = false)
    private Long grupoId;
}