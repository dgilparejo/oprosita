import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlankComponent } from '../../components/plank/plank.component';
import { UiButtonComponent } from '../../components/ui-button/ui-button.component';
import { UiItemComponent } from '../../components/ui-item/ui-item.component';
import { AddContentDialogComponent } from '../../components/add-content-dialog/add-content-dialog.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { SimulacrosService } from '../../api/api/simulacros.service';
import { ArchivosService } from '../../api/api/archivos.service';
import { Simulacro } from '../../api/model/simulacro';
import { KeycloakService } from '../../services/keycloak.service';
import { NovedadConArchivo } from '../../model/NovedadConArchivo';
import {DomSanitizer} from '@angular/platform-browser';

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
  archivoInfo: Map<number, { nombre: string; tipo: string }> = new Map();
  isProfesor = false;

  constructor(
    private simulacrosService: SimulacrosService,
    private archivosService: ArchivosService,
    private dialog: MatDialog,
    private keycloakService: KeycloakService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.isProfesor = this.keycloakService.hasRole('profesor');
    this.loadSimulacros();

    window.addEventListener('abrir-simulacro', (event: any) => {
      const id = event.detail;
      this.abrirSimulacro(id);
    });
  }

  loadSimulacros(): void {
    this.simulacrosService.getSimulacros().subscribe({
      next: (data: Simulacro[]) => {
        this.simulacros = data;
        this.simulacros.forEach(simulacro => {
          if (simulacro.archivoId != null) {
            this.archivosService.getArchivoInfo(simulacro.archivoId).subscribe({
              next: (archivo) => {
                this.archivoInfo.set(simulacro.id!, {
                  nombre: archivo.nombre!,
                  tipo: archivo.tipo!
                });
              },
              error: (err) => console.error('Error obteniendo archivo', err)
            });
          }
        });
      },
      error: (err: any) => console.error('Error cargando simulacros', err)
    });
  }

  addSimulation(): void {
    const dialogRef = this.dialog.open(AddContentDialogComponent, {
      data: {
        fixedTipo: 'Simulacro',
        hideFechaHora: true,
        hideUrl: true
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result?.descripcion && result?.documentFile) {
        this.simulacrosService
          .createSimulacro(result.descripcion, result.documentFile)
          .subscribe({
            next: (simulacro: Simulacro) => {
              this.simulacros.push(simulacro);
              if (simulacro.archivoId != null) {
                this.archivosService.getArchivoInfo(simulacro.archivoId).subscribe({
                  next: (archivo) => {
                    this.archivoInfo.set(simulacro.id!, {
                      nombre: archivo.nombre!,
                      tipo: archivo.tipo!
                    });
                  },
                  error: (err) => console.error('Error obteniendo archivo', err)
                });
              }
            },
            error: (err: any) => console.error('Error al subir simulacro', err)
          });
      }
    });
  }

  get simulacrosAsNovedades(): NovedadConArchivo[] {
    return this.simulacros.map((s) => {
      const archivo = this.archivoInfo.get(s.id!);
      let texto = s.descripcion ?? 'Simulacro';

      if (archivo?.nombre) {
        const enlace = `<a class="archivo-link" data-id="${s.id}" style="text-decoration: underline; color: var(--forest-green); cursor: pointer">${archivo.nombre}</a>`;
        texto += ` (${enlace})`;
      }

      return {
        id: s.id,
        archivoId: s.archivoId ?? undefined,
        texto: this.sanitizer.bypassSecurityTrustHtml(texto) as unknown as string,
        fechaCreacion: new Date().toISOString(),
        tipoDestinatario: 'alumno'
      };
    });
  }

  abrirSimulacro(id: number): void {
    const simulacro = this.simulacros.find(s => s.id === id);
    if (!simulacro?.archivoId) return;

    this.archivosService.downloadArchivo(simulacro.archivoId, 'body').subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      window.open(url, '_blank');
    });
  }

  descargarSimulacro(id: number): void {
    const simulacro = this.simulacros.find(s => s.id === id);
    if (!simulacro?.archivoId) return;

    this.archivosService.downloadArchivo(simulacro.archivoId, 'body').subscribe(blob => {
      const archivo = this.archivoInfo.get(simulacro.id!);
      const nombre = archivo?.nombre ?? 'archivo.pdf';

      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = nombre;
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }

  removeSimulacro(id: number): void {
    this.simulacrosService.deleteSimulacro(id).subscribe({
      next: () => {
        this.simulacros = this.simulacros.filter(s => s.id !== id);
        this.archivoInfo.delete(id);
      },
      error: (err: any) => console.error('Error al eliminar simulacro', err)
    });
  }
}
