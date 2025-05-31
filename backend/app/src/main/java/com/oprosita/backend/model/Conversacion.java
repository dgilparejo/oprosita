package com.oprosita.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conversaciones")
public class Conversacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El usuarioId es obligatorio")
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @NotNull(message = "El otroUsuarioId es obligatorio")
    @Column(name = "otro_usuario_id", nullable = false)
    private Long otroUsuarioId;

    @Size(max = 255, message = "El último mensaje no puede tener más de 255 caracteres")
    @Column(name = "ultimo_mensaje", length = 255)
    private String ultimoMensaje;

    @Column(name = "fecha_ultimo_mensaje")
    private LocalDateTime fechaUltimoMensaje;

    @Min(value = 0, message = "El número de no leídos no puede ser negativo")
    @Column(name = "no_leidos")
    private Integer noLeidos;
}
