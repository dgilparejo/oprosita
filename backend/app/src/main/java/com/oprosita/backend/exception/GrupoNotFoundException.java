package com.oprosita.backend.exception;

public class GrupoNotFoundException extends RuntimeException {
    public GrupoNotFoundException(Long id) {
        super("Grupo no encontrado con ID: " + id);
    }
}
