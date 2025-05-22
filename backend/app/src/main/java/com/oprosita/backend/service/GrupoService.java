package com.oprosita.backend.service;

import com.oprosita.backend.model.generated.Grupo;
import java.util.List;

public interface GrupoService {
    Grupo obtenerPorId(Long id);
    List<Grupo> obtenerTodos();
    Grupo crear(Grupo grupo);
    Grupo actualizar(Long id, Grupo grupo);
    void eliminar(Long id);
}
