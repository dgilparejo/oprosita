package com.oprosita.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reunion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false)
    private String titulo;

    @Column(length = 1000)
    private String descripcion;

    @NotNull(message = "La fecha y hora de la reunión es obligatoria")
    @Future(message = "La fecha y hora deben ser futuras")
    @Column(name = "fecha_hora", nullable = false)
    private OffsetDateTime fechaHora;

    @Column(nullable = false)
    private String enlace;

    @OneToOne(optional = false)
    @JoinColumn(name = "grupo_id", nullable = false, unique = true)
    @NotNull(message = "Debe estar asociada a un grupo")
    private Grupo grupo;
}
