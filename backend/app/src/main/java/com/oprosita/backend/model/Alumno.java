package com.oprosita.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("alumno")
public class Alumno extends Usuario {

    @ManyToOne(optional = false)
    @JoinColumn(name = "grupo_id", nullable = false)
    @NotNull(message = "El alumno debe pertenecer a un grupo")
    private Grupo grupo;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    private List<ContenidoItem> contenidos;
}