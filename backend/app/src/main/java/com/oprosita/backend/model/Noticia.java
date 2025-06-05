package com.oprosita.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.net.URI;

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

    @Column(nullable = true, length = 1000)
    private String descripcion;

    private URI url;

    @OneToOne(optional = true)
    @JoinColumn(name = "archivo_id", nullable = true)
    private Archivo archivo;
}
