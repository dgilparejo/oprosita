package com.oprosita.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("profesor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profesor extends Usuario {
}

