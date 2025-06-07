import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PlankComponent } from '../../components/plank/plank.component';
import { UiButtonComponent } from '../../components/ui-button/ui-button.component';
import { UiItemComponent } from '../../components/ui-item/ui-item.component';
import { AddContentDialogComponent } from '../../components/add-content-dialog/add-content-dialog.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ContenidoService, GruposService, Novedad, UsuariosService, ArchivosService } from '../../api';
import TipoDestinatarioEnum = Novedad.TipoDestinatarioEnum;
import { DomSanitizer } from '@angular/platform-browser';
import { NovedadConArchivo } from '../../model/NovedadConArchivo';
import {KeycloakService} from '../../services/keycloak.service';

@Component({
  selector: 'app-month-detail',
  standalone: true,
  imports: [
    CommonModule,
    PlankComponent,
    UiButtonComponent,
    UiItemComponent,
    MatDialogModule
  ],
  templateUrl: './monthdetail.component.html',
  styleUrls: ['./monthdetail.component.css']
})
export class MonthDetailComponent implements OnInit {
  grupoId = '';
  mesId = '';
  mesNombre = '';

  contenido: Record<'temas' | 'programacion' | 'practico', { id: number; texto: string; archivoId?: number }[]> = {
    temas: [],
    programacion: [],
    practico: []
  };

  archivoInfo: Map<number, { nombre: string; tipo: string }> = new Map();

  private route = inject(ActivatedRoute);
  private contenidoService = inject(ContenidoService);
  private gruposService = inject(GruposService);
  private usuariosService = inject(UsuariosService);
  private archivosService = inject(ArchivosService);
  private keycloakService = inject(KeycloakService);
  private dialog = inject(MatDialog);
  private sanitizer = inject(DomSanitizer);
  isProfesor = false;

  ngOnInit(): void {
    this.isProfesor = this.keycloakService.hasRole('profesor');
    const nombreGrupo = this.route.snapshot.paramMap.get('id') || '';
    const nombreMes = this.route.snapshot.paramMap.get('mes') || '';
    this.mesNombre = nombreMes;

    this.gruposService.getGrupos().subscribe(grupos => {
      const grupo = grupos.find(g => g.nombre.toLowerCase().replace(/\s/g, '-') === nombreGrupo);
      if (!grupo) return;

      this.grupoId = grupo.id!.toString();

      this.gruposService.getMesesByGrupo(grupo.id!).subscribe(meses => {
        const mes = meses.find(m => m.nombre === nombreMes);
        if (!mes) return;

        this.mesId = mes.id!.toString();

        this.contenidoService.getContenidoGrupoMes(+this.grupoId, +this.mesId).subscribe(data => {
          data.forEach(item => {
            const tipo = item.tipoContenido as keyof typeof this.contenido;
            if (this.contenido[tipo]) {
              this.contenido[tipo].push({
                id: item.id!,
                texto: item.texto,
                archivoId: item.archivoId ?? undefined
              });
            }

            if (item.archivoId) {
              this.archivosService.getArchivoInfo(item.archivoId).subscribe(archivo => {
                this.archivoInfo.set(item.id!, {
                  nombre: archivo.nombre!,
                  tipo: archivo.tipo!
                });
              });
            }
          });
        });
      });
    });
  }

  addContent(): void {
    const dialogRef = this.dialog.open(AddContentDialogComponent, {
      data: {
        hideUrl: true,
        hideFechaHora: true
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.usuariosService.getMiUsuario().subscribe(usuario => {
          if (!usuario.id) return;

          this.contenidoService
            .addContenidoToGrupoMes(
              +this.grupoId,
              +this.mesId,
              result.descripcion,
              result.tipo,
              usuario.id,
              result.documentFile || undefined
            )
            .subscribe({
              next: (contenidoCreado) => {
                const nuevoItem = {
                  id: contenidoCreado.id!,
                  texto: contenidoCreado.texto!,
                  archivoId: contenidoCreado.archivoId ?? undefined
                };

                this.contenido[contenidoCreado.tipoContenido as keyof typeof this.contenido].push(nuevoItem);

                if (contenidoCreado.archivoId) {
                  this.archivosService.getArchivoInfo(contenidoCreado.archivoId).subscribe({
                    next: archivo => {
                      this.archivoInfo.set(contenidoCreado.id!, {
                        nombre: archivo.nombre!,
                        tipo: archivo.tipo!
                      });
                    },
                    error: err => console.error('Error cargando archivo tras creación:', err)
                  });
                }
              },
              error: err => console.error('Error al enviar contenido:', err)
            });
        });
      }
    });
  }

  abrirArchivo(id: number): void {
    const item = [...this.contenido.temas, ...this.contenido.programacion, ...this.contenido.practico].find(c => c.id === id);
    if (!item?.archivoId) return;

    this.archivosService.downloadArchivo(item.archivoId, 'body').subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      window.open(url, '_blank');
    });
  }

  private mapContenidoToNovedades(arr: { id: number; texto: string; archivoId?: number }[]): NovedadConArchivo[] {
    return arr.map((item) => {
      let texto = item.texto;
      const archivo = item.archivoId ? this.archivoInfo.get(item.id) : undefined;

      if (archivo?.nombre) {
        const enlace = `<a class="archivo-link" data-id="${item.id}" style="text-decoration: underline; color: var(--forest-green); cursor: pointer">${archivo.nombre}</a>`;
        texto += ` (${enlace})`;
      }

      return {
        id: item.id,
        archivoId: item.archivoId,
        texto: this.sanitizer.bypassSecurityTrustHtml(texto) as unknown as string,
        fechaCreacion: new Date().toISOString(),
        tipoDestinatario: TipoDestinatarioEnum.Alumno
      };
    });
  }

  get temasAsNovedades(): NovedadConArchivo[] {
    return this.mapContenidoToNovedades(this.contenido.temas);
  }

  get programacionAsNovedades(): NovedadConArchivo[] {
    return this.mapContenidoToNovedades(this.contenido.programacion);
  }

  get practicoAsNovedades(): NovedadConArchivo[] {
    return this.mapContenidoToNovedades(this.contenido.practico);
  }

  removeContenido(id: number): void {
    this.contenidoService.deleteContenido(id).subscribe({
      next: () => {
        // Eliminar el contenido de todos los arrays si está
        (['temas', 'programacion', 'practico'] as const).forEach(tipo => {
          this.contenido[tipo] = this.contenido[tipo].filter(c => c.id !== id);
        });
        this.archivoInfo.delete(id);
      },
      error: (err) => {
        console.error('Error al eliminar contenido', err);
      }
    });
  }

}
