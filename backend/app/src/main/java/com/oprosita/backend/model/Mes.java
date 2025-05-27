package com.oprosita.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del mes es obligatorio")
    private String nombre;

    @ManyToOne(optional = false)
    @JoinColumn(name = "grupo_id", nullable = false)
    @NotNull(message = "El mes debe estar asignado a un grupo")
    private Grupo grupo;

    @OneToMany(mappedBy = "mes", cascade = CascadeType.ALL)
    private List<ContenidoItem> contenidos;
}
