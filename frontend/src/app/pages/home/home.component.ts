import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlankComponent } from '../../components/plank/plank.component';
import { CalendarComponent } from '../../components/calendar/calendar.component';
import { UiButtonComponent } from '../../components/ui-button/ui-button.component';
import { NovedadesService } from '../../api/api/novedades.service';
import { Novedad } from '../../api/model/novedad';
import {KeycloakService} from '../../services/keycloak.service';

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
  esProfesor = false;

  constructor(
    private novedadesService: NovedadesService,
    private keycloakService: KeycloakService
  ) {}

  ngOnInit(): void {
    this.esProfesor = this.keycloakService.hasRole('profesor');

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
}
