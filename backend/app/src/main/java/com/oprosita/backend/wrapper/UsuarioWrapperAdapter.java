package com.oprosita.backend.wrapper;

import com.oprosita.backend.model.generated.Alumno;
import com.oprosita.backend.model.generated.Profesor;
import com.oprosita.backend.model.generated.Usuario;

public class UsuarioWrapperAdapter extends Usuario {

    public UsuarioWrapperAdapter(Object obj) {
        if (obj instanceof Alumno alumno) {
            this.setTipo(TipoEnum.valueOf("ALUMNO"));
            this.setId(alumno.getId());
            this.setNombre(alumno.getNombre());
            this.setGrupoId(alumno.getGrupoId());
            this.setGrupoIds(null);
            this.setIdKeycloak(alumno.getIdKeycloak());
        } else if (obj instanceof Profesor profesor) {
            this.setTipo(TipoEnum.valueOf("PROFESOR"));
            this.setId(profesor.getId());
            this.setNombre(profesor.getNombre());
            this.setGrupoId(null);
            this.setGrupoIds(profesor.getGrupoIds());
            this.setIdKeycloak(profesor.getIdKeycloak());
        } else {
            throw new IllegalArgumentException("Tipo de objeto inesperado: " + obj.getClass().getName());
        }
    }
}
