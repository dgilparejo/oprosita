import {Component, OnInit, inject, ChangeDetectorRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsuariosService, ContenidoService, ArchivosService, Grupo, GruposService, Novedad } from '../../api';
import { KeycloakService } from '../../services/keycloak.service';
import { PlankComponent } from '../../components/plank/plank.component';
import { UiButtonComponent } from '../../components/ui-button/ui-button.component';
import { UiItemComponent } from '../../components/ui-item/ui-item.component';
import { AddContentDialogComponent } from '../../components/add-content-dialog/add-content-dialog.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import {Router} from '@angular/router';
import { ListComponent } from '../../components/list/list.component';
import { NovedadConArchivo } from '../../model/NovedadConArchivo';

@Component({
  selector: 'app-student-body',
  standalone: true,
  imports: [
    CommonModule,
    PlankComponent,
    UiButtonComponent,
    UiItemComponent,
    MatDialogModule,
    ListComponent
  ],
  templateUrl: './student-body.component.html',
  styleUrl: './student-body.component.css'
})
export class StudentBodyComponent implements OnInit {
  isAlumno = false;
  isProfesor = false;
  usuarioId: number | null = null;
  alumnoNombre = '';
  grupos: Grupo[] = [];

  contenido: Record<'temas' | 'programacion' | 'practico', { id: number; texto: string; archivoId?: number }[]> = {
    temas: [],
    programacion: [],
    practico: []
  };

  archivoInfo: Map<number, { nombre: string; tipo: string }> = new Map();
  private router = inject(Router);
  private usuariosService = inject(UsuariosService);
  private contenidoService = inject(ContenidoService);
  private archivosService = inject(ArchivosService);
  private gruposService = inject(GruposService);
  private keycloakService = inject(KeycloakService);
  private dialog = inject(MatDialog);
  private sanitizer = inject(DomSanitizer);

  get nombresGrupos(): string[] {
    return this.grupos.map(g => g.nombre);
  }

  ngOnInit(): void {
    this.isAlumno = this.keycloakService.hasRole('alumno');
    this.isProfesor = this.keycloakService.hasRole('profesor');

    this.usuariosService.getMiUsuario().subscribe(usuario => {
      this.usuarioId = usuario.id ?? null;

      if (this.isAlumno && this.usuarioId) {
        this.alumnoNombre = usuario.nombre!;
        this.cargarContenidoPrivadoAlumno(this.usuarioId);
      } else if (this.isProfesor && this.usuarioId) {
        this.gruposService.getGruposByProfesor(this.usuarioId).subscribe({
          next: res => this.grupos = res,
          error: err => console.error('Error cargando grupos del profesor', err)
        });
      }
    });
  }

  cargarContenidoPrivadoAlumno(alumnoId: number): void {
    this.contenidoService.getContenidoPrivadoAlumno(alumnoId).subscribe(data => {
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
  }

  abrirArchivo(id: number): void {
    const item = [...this.contenido.temas, ...this.contenido.programacion, ...this.contenido.practico].find(c => c.id === id);
    if (!item?.archivoId) return;

    this.archivosService.downloadArchivo(item.archivoId, 'body').subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      window.open(url, '_blank');
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
      if (result && this.usuarioId) {
        const tipoContenido = result.tipo.toUpperCase();

        this.contenidoService
          .addContenidoPrivadoAlumno(this.usuarioId, result.descripcion, tipoContenido, result.documentFile || undefined)
          .subscribe({
            next: () => {
              this.contenido = { temas: [], programacion: [], practico: [] };
              this.archivoInfo.clear();
              this.cargarContenidoPrivadoAlumno(this.usuarioId!);
            },
            error: err => console.error('Error al añadir contenido privado', err)
          });
      }
    });
  }

  removeContenido(id: number): void {
    this.contenidoService.deleteContenido(id).subscribe(() => {
      (['temas', 'programacion', 'practico'] as const).forEach(tipo => {
        this.contenido[tipo] = this.contenido[tipo].filter(c => c.id !== id);
      });
      this.archivoInfo.delete(id);
    });
  }

  mapContenidoToNovedades(arr: { id: number; texto: string; archivoId?: number }[]): NovedadConArchivo[] {
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
        tipoDestinatario: Novedad.TipoDestinatarioEnum.Alumno
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

  irAMeses(nombreGrupo: string): void {
    const id = nombreGrupo.toLowerCase().replace(/\s/g, '-');
    this.router.navigate([`/alumnado/${id}`]);
  }
}
