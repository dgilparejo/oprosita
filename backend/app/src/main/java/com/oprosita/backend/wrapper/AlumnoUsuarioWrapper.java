package com.oprosita.backend.wrapper;

import com.oprosita.backend.model.generated.Alumno;

public class AlumnoUsuarioWrapper implements UsuarioWrapper {
    private final Alumno alumno;

    public AlumnoUsuarioWrapper(Alumno alumno) {
        this.alumno = alumno;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    @Override
    public String getTipo() {
        return "alumno";
    }
}
