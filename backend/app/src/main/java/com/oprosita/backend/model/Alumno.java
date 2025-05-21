package com.oprosita.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@DiscriminatorValue("alumno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alumno extends Usuario {
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    private List<ContenidoItem> contenidos;
}
