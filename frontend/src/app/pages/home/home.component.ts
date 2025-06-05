import { forkJoin } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlankComponent } from '../../components/plank/plank.component';
import { CalendarComponent } from '../../components/calendar/calendar.component';
import { UiButtonComponent } from '../../components/ui-button/ui-button.component';
import { NovedadesService } from '../../api/api/novedades.service';
import { MatDialog } from '@angular/material/dialog';
import { AddContentDialogComponent } from '../../components/add-content-dialog/add-content-dialog.component';

import {
  Grupo,
  GruposService,
  Novedad,
  Reunion,
  ReunionesService,
  UsuariosService
} from '../../api';
import { KeycloakService } from '../../services/keycloak.service';
import { UsuarioHelperService } from '../../services/usuario-helper.service';

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
  gruposProfesor: Grupo[] = [];

  constructor(
    private novedadesService: NovedadesService,
    private keycloakService: KeycloakService,
    private reunionesService: ReunionesService,
    private gruposService: GruposService,
    private usuariosService: UsuariosService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.usuariosService.getMiUsuario().subscribe({
      next: usuario => {
        this.esProfesor = this.keycloakService.hasRole('profesor');

        if (this.esProfesor && usuario.tipo === 'profesor') {
          const profesorId = usuario.id;
          if (!profesorId) return;

          this.gruposService.getGruposByProfesor(profesorId).pipe(
            switchMap(grupos => {
              this.gruposProfesor = grupos;
              const requests = grupos.map(g =>
                this.reunionesService.getReunionesByGrupo(g.id!)
              );
              return forkJoin(requests);
            }),
            map(resArrays => resArrays.flat())
          ).subscribe({
            next: reuniones => {
              this.reuniones = reuniones;
            },
            error: err =>
              console.error('Error cargando reuniones profesor', err)
          });

          this.novedadesService.getNovedadesProfesor().subscribe({
            next: res => (this.novedadesProfesor = res),
            error: err =>
              console.error('Error cargando novedades profesor', err)
          });

        } else if (!this.esProfesor && usuario.tipo === 'alumno') {
          if ('grupoId' in usuario) {
            const grupoId = usuario.grupoId;
            if (!grupoId) {
              console.warn('⚠grupoId no definido en alumno');
              return;
            }

            this.reunionesService.getReunionesByGrupo(grupoId).subscribe({
              next: reuniones => {
                this.reuniones = reuniones;
              },
              error: err =>
                console.error('Error cargando reuniones alumno', err)
            });
          }
        }

        // Siempre cargar novedades para alumno
        this.novedadesService.getNovedadesAlumno().subscribe({
          next: res => {
            this.novedadesAlumno = res;
          },
          error: err =>
            console.error('Error cargando novedades alumno', err)
        });
      },
      error: err => console.error('Error obteniendo el usuario', err)
    });
  }


  addNuevaNovedad(nueva: string) {
    const novedad: Novedad = {
      texto: nueva,
      tipoDestinatario: 'alumno',
      fechaCreacion: new Date().toISOString()
    };

    this.novedadesService.createNovedadAlumno(novedad).subscribe({
      next: res => this.novedadesAlumno.push(res),
      error: err => console.error('Error al crear novedad de alumno', err)
    });
  }

  removeNovedadAlumno(id: number) {
    this.novedadesService.deleteNovedad(id).subscribe({
      next: () =>
        (this.novedadesAlumno = this.novedadesAlumno.filter(n => n.id !== id)),
      error: err => console.error('Error al eliminar novedad', err)
    });
  }

  addNuevaNovedadProfesor(nueva: string): void {
    const novedad: Novedad = {
      texto: nueva,
      tipoDestinatario: 'profesor',
      fechaCreacion: new Date().toISOString()
    };

    this.novedadesService.createNovedadProfesor(novedad).subscribe({
      next: res => this.novedadesProfesor.push(res),
      error: err => console.error('Error al crear novedad de profesor', err)
    });
  }

  removeNovedadProfesor(id: number): void {
    this.novedadesService.deleteNovedad(id).subscribe({
      next: () =>
        (this.novedadesProfesor = this.novedadesProfesor.filter(n => n.id !== id)),
      error: err => console.error('Error al eliminar novedad del profesor', err)
    });
  }

  onCrearReunion() {
    console.log('Abriendo diálogo para crear reunión');

    const dialogRef = this.dialog.open(AddContentDialogComponent, {
      data: {
        fixedTipo: 'reunion',
        hideFileUpload: true,
        gruposDisponibles: this.gruposProfesor
      },
      width: '500px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Diálogo cerrado. Resultado:', result);

      if (!result) {
        console.warn('⚠No se devolvió resultado del diálogo');
        return;
      }

      const grupoId = result.grupoId;
      console.log('Grupo seleccionado:', grupoId);

      if (!grupoId) {
        console.error('No se ha seleccionado un grupo. Abortando.');
        return;
      }

      const fecha = result.fechaHora instanceof Date
        ? result.fechaHora.toISOString()
        : new Date().toISOString();

      const nuevaReunion: Reunion = {
        titulo: result.descripcion,
        descripcion: result.descripcion,
        enlace: result.url,
        fechaHora: fecha,
        grupoId
      };

      console.log('Enviando reunión al backend:', nuevaReunion);

      this.reunionesService.createReunion(grupoId, nuevaReunion).subscribe({
        next: r => {
          console.log('Reunión creada con éxito:', r);
          this.reuniones.push(r);
        },
        error: err => console.error('Error creando reunión', err)
      });
    });
  }
}
