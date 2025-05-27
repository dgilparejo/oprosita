package com.oprosita.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("profesor")
public class Profesor extends Usuario {

    @ManyToMany
    @JoinTable(
            name = "profesor_grupo",
            joinColumns = @JoinColumn(name = "profesor_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id")
    )
    @Size(min = 1, message = "El profesor debe estar asignado a al menos un grupo")
    private List<Grupo> grupos;
}

