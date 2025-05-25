package com.oprosita.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("profesor")
public class Profesor extends Usuario {
}
