package com.oprosita.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "El tipo de contenido es obligatorio")
    private TipoContenido tipoContenido;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mes_id", nullable = false)
    @NotNull(message = "El contenido debe estar asignado a un mes")
    private Mes mes;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    @NotNull(message = "El contenido debe tener un autor")
    private Usuario autor;

    @OneToOne
    @JoinColumn(name = "archivo_id")
    private Archivo archivo;
}
