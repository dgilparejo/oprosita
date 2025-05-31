package com.oprosita.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("profesor")
public class Profesor extends Usuario {

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
    private List<Grupo> grupos;
}

