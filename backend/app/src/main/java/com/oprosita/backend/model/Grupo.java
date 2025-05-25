package com.oprosita.backend.model;

import jakarta.persistence.*;
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
    private String nombre;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private List<Mes> meses;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;
}