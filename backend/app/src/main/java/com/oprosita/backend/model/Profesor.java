package com.oprosita.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@DiscriminatorValue("profesor")
@Entity
public class Profesor extends Usuario {
}

