import {forkJoin, of} from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlankComponent } from '../../components/plank/plank.component';
import { CalendarComponent } from '../../components/calendar/calendar.component';
import { UiButtonComponent } from '../../components/ui-button/ui-button.component';
import { NovedadesService } from '../../api/api/novedades.service';
import {GruposService, Novedad, Reunion, ReunionesService, UsuariosService} from '../../api';
import { KeycloakService } from '../../services/keycloak.service';
import {UsuarioHelperService} from '../../services/usuario-helper.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    PlankComponent,
    CalendarComponent,
    UiButtonComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  novedadesProfesor: Novedad[] = [];
  novedadesAlumno: Novedad[] = [];
  reuniones: Reunion[] = [];
  esProfesor = false;

  constructor(
    private novedadesService: NovedadesService,
    private keycloakService: KeycloakService,
    private reunionesService: ReunionesService,
    private gruposService: GruposService,
    private usuariosService: UsuariosService,
    private usuarioHelper: UsuarioHelperService
  ) {}

  ngOnInit(): void {
    this.esProfesor = this.keycloakService.hasRole('profesor');

    this.usuariosService.getMiUsuario().subscribe({
      next: usuario => {
        console.log('Usuario recibido:', usuario);

        if (this.usuarioHelper.isProfesor(usuario)) {
          const profesorId = this.usuarioHelper.getIdIfProfesor(usuario);
          if (!profesorId) return;

          this.gruposService.getGruposByProfesor(profesorId).pipe(
            switchMap(grupos => {
              console.log('Grupos del profesor:', grupos);
              const requests = grupos.map(g => this.reunionesService.getReunionesByGrupo(g.id!));
              return forkJoin(requests);
            }),
            map(resArrays => resArrays.flat())
          ).subscribe({
            next: reuniones => {
              console.log('Reuniones profesor:', reuniones);
              this.reuniones = reuniones;
            },
            error: err => console.error('Error cargando reuniones profesor', err)
          });

        } else if (this.usuarioHelper.isAlumno(usuario)) {
          const grupoId = this.usuarioHelper.getGrupoIdIfAlumno(usuario);
          if (!grupoId) return;

          this.reunionesService.getReunionesByGrupo(grupoId).subscribe({
            next: reuniones => {
              console.log('Reuniones alumno:', reuniones);
              this.reuniones = reuniones;
            },
            error: err => console.error('Error cargando reuniones alumno', err)
          });
        }
      },
      error: err => console.error('Error obteniendo el usuario', err)
    });

    // Novedades
    this.novedadesService.getNovedadesAlumno().subscribe({
      next: res => this.novedadesAlumno = res,
      error: err => console.error('Error cargando novedades alumno', err)
    });

    if (this.esProfesor) {
      this.novedadesService.getNovedadesProfesor().subscribe({
        next: res => this.novedadesProfesor = res,
        error: err => console.error('Error cargando novedades profesor', err)
      });
    }
  }

  addNuevaNovedad(nueva: string) {
    const novedad: Novedad = {
      texto: nueva,
      tipoDestinatario: 'alumno',
      fechaCreacion: new Date().toISOString()
    };

    this.novedadesService.createNovedadAlumno(novedad).subscribe({
      next: (res) => this.novedadesAlumno.push(res),
      error: (err) => console.error('Error al crear novedad de alumno', err)
    });
  }

  removeNovedadAlumno(id: number) {
    this.novedadesService.deleteNovedad(id).subscribe({
      next: () => this.novedadesAlumno = this.novedadesAlumno.filter(n => n.id !== id),
      error: (err) => console.error('Error al eliminar novedad', err)
    });
  }

  onCrearReunion() {
    // Aquí podrías lanzar un modal o redirigir a una pantalla para crear reuniones.
    console.log('Crear nueva reunión...');
  }
}
