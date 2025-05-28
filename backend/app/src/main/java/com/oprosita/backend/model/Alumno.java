package com.oprosita.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("alumno")
public class Alumno extends Usuario {

    @ManyToOne(optional = true)
    @JoinColumn(name = "grupo_id", nullable = true)
    private Grupo grupo;

    @OneToMany(mappedBy = "autor")
    private List<ContenidoItem> contenidos;
}