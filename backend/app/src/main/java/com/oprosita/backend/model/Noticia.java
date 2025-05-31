package com.oprosita.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String descripcion;

    @OneToOne(optional = true)
    @JoinColumn(name = "archivo_id", nullable = true)
    private Archivo archivo;
}
