import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlankComponent } from '../../components/plank/plank.component';
import { UiButtonComponent } from '../../components/ui-button/ui-button.component';
import { UiItemComponent } from '../../components/ui-item/ui-item.component';
import { AddContentDialogComponent } from '../../components/add-content-dialog/add-content-dialog.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { SimulacrosService } from '../../api/api/simulacros.service';
import { Simulacro } from '../../api/model/simulacro';
import { KeycloakService } from '../../services/keycloak.service';
import {Novedad} from '../../api';

@Component({
  selector: 'app-simulation',
  standalone: true,
  imports: [
    CommonModule,
    PlankComponent,
    UiButtonComponent,
    UiItemComponent,
    MatDialogModule
  ],
  templateUrl: './simulation.component.html',
  styleUrls: ['./simulation.component.css']
})
export class SimulationComponent implements OnInit {
  simulacros: Simulacro[] = [];
  isProfesor = false;

  constructor(
    private simulacrosService: SimulacrosService,
    private dialog: MatDialog,
    private keycloakService: KeycloakService
  ) {}

  ngOnInit(): void {
    this.isProfesor = this.keycloakService.hasRole('profesor');
    this.loadSimulacros();
  }

  loadSimulacros(): void {
    this.simulacrosService.getSimulacros().subscribe({
      next: (data: Simulacro[]) => (this.simulacros = data),
      error: (err: any) => console.error('Error cargando simulacros', err)
    });
  }

  addSimulation(): void {
    const dialogRef = this.dialog.open(AddContentDialogComponent, {
      data: { fixedTipo: 'Simulacro' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result?.descripcion && result?.documentFile) {
        this.simulacrosService
          .createSimulacro(result.descripcion, result.documentFile)
          .subscribe({
            next: (simulacro: Simulacro) => {
              this.simulacros.push(simulacro);
            },
            error: (err: any) => console.error('Error al subir simulacro', err)
          });
      }
    });
  }

  get simulacrosAsNovedades(): Novedad[] {
    return this.simulacros.map((s) => ({
      id: s.id,
      texto: s.descripcion ?? 'Simulacro',
      fechaCreacion: new Date().toISOString(),
      tipoDestinatario: 'alumno'
    }));
  }

  removeSimulacro(id: number): void {
    this.simulacrosService.deleteSimulacro(id).subscribe({
      next: () => {
        this.simulacros = this.simulacros.filter(s => s.id !== id);
      },
      error: (err: any) => console.error('Error al eliminar simulacro', err)
    });
  }
}
