package com.oprosita.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContenidoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    @Enumerated(EnumType.STRING)
    private TipoContenido tipoContenido;

    private String mes;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    private Long archivoId;
}
