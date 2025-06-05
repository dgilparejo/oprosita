import { Injectable } from '@angular/core';
import { Usuario, Alumno, Profesor, GruposService, ReunionesService } from '../api';

@Injectable({
  providedIn: 'root'
})
export class UsuarioHelperService {

  isAlumno(usuario: Usuario): usuario is Alumno {
    return usuario.tipo === 'alumno';
  }

  isProfesor(usuario: Usuario): usuario is Profesor {
    return usuario.tipo === 'profesor';
  }

  getGrupoIdIfAlumno(usuario: Usuario): number | null {
    return this.isAlumno(usuario) ? usuario.grupoId : null;
  }

  getIdIfProfesor(usuario: Usuario): number | null {
    return this.isProfesor(usuario) ? usuario.id ?? null : null;
  }
}
