package com.oprosita.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "El nombre del grupo es obligatorio")
    private String nombre;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(min = 1, message = "El grupo debe contener al menos un mes")
    private List<Mes> meses;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alumno> alumnos;//1 o mas alumnos por grupo

    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;//1 solo profesor por grupo

    @OneToOne(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Reunion reunion;
}
