package com.oprosita.backend.wrapper;

import com.oprosita.backend.model.generated.Profesor;

public class ProfesorUsuarioWrapper implements UsuarioWrapper {
    private final Profesor profesor;

    public ProfesorUsuarioWrapper(Profesor profesor) {
        this.profesor = profesor;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    @Override
    public String getTipo() {
        return "profesor";
    }
}