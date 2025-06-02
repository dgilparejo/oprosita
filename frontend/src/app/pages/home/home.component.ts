import { forkJoin } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlankComponent } from '../../components/plank/plank.component';
import { CalendarComponent } from '../../components/calendar/calendar.component';
import { UiButtonComponent } from '../../components/ui-button/ui-button.component';
import { NovedadesService } from '../../api/api/novedades.service';
import {GruposService, Novedad, Reunion, ReunionesService} from '../../api';
import { KeycloakService } from '../../services/keycloak.service';

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
    private gruposService: GruposService
  ) {}

  ngOnInit(): void {
    this.esProfesor = this.keycloakService.hasRole('profesor');
    const userId = this.keycloakService.getUserId();

    if (this.esProfesor && userId) {
      const profesorId = Number(userId);

      this.gruposService.getGruposByProfesor(profesorId).pipe(
        switchMap((grupos: any[]) => {
          const grupoIds = grupos.map(g => g.id);
          const requests = grupoIds.map(id => this.reunionesService.getReunionesByGrupo(id));
          return forkJoin(requests);
        }),
        map((resArrays: Reunion[][]) => resArrays.flat())
      ).subscribe({
        next: (res: Reunion[]) => this.reuniones = res,
        error: (err: any) => console.error('Error cargando reuniones de profesor', err)
      });
    } else {
      this.keycloakService.getGrupoIdFromToken().subscribe((grupoId: number) => {
        this.reunionesService.getReunionesByGrupo(grupoId).subscribe({
          next: (res) => this.reuniones = res,
          error: (err) => console.error('Error cargando reuniones del grupo del alumno', err)
        });
      });
    }

    this.novedadesService.getNovedadesAlumno().subscribe({
      next: (res: Novedad[]) => this.novedadesAlumno = res,
      error: (err) => console.error('Error cargando novedades alumno', err)
    });

    if (this.esProfesor) {
      this.novedadesService.getNovedadesProfesor().subscribe({
        next: (res: Novedad[]) => this.novedadesProfesor = res,
        error: (err) => console.error('Error cargando novedades profesor', err)
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
