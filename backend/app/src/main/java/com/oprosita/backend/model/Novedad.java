package com.oprosita.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Novedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    @Enumerated(EnumType.STRING)
    private TipoDestinatario tipoDestinatario;

    private LocalDateTime fechaCreacion;
}
