package com.oprosita.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("alumno")
public class Alumno extends Usuario {
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    private List<ContenidoItem> contenidos;
}