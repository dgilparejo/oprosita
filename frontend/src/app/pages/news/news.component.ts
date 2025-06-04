import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlankComponent } from '../../components/plank/plank.component';
import { UiItemComponent } from '../../components/ui-item/ui-item.component';
import { UiButtonComponent } from '../../components/ui-button/ui-button.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { NoticiasService } from '../../api/api/noticias.service';
import { Noticia } from '../../api/model/noticia';
import { AddContentDialogComponent } from '../../components/add-content-dialog/add-content-dialog.component';
import { KeycloakService } from '../../services/keycloak.service';
import { NoticiaConPlank } from '../../model/NoticiaConPlank';
import { DomSanitizer } from '@angular/platform-browser';
import {ArchivosService} from '../../api';

@Component({
  selector: 'app-news',
  standalone: true,
  imports: [
    CommonModule,
    PlankComponent,
    UiItemComponent,
    UiButtonComponent,
    MatDialogModule
  ],
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {
  noticias: Noticia[] = [];
  isProfesor = false;
  archivoInfo: Map<number, { nombre: string; tipo: string }> = new Map();

  constructor(
    private noticiasService: NoticiasService,
    private keycloakService: KeycloakService,
    private dialog: MatDialog,
    private sanitizer: DomSanitizer,
    private archivosService: ArchivosService,
  ) {}

  ngOnInit(): void {
    this.isProfesor = this.keycloakService.hasRole('profesor');
    this.loadNoticias();
  }

  loadNoticias(): void {
    this.noticiasService.getNoticias().subscribe({
      next: (data) => {
        this.noticias = data;
        this.noticias.forEach(n => {
          if (n.archivoId != null) {
            this.archivosService.getArchivoInfo(n.archivoId).subscribe({
              next: (archivo) => {
                this.archivoInfo.set(n.id!, {
                  nombre: archivo.nombre!,
                  tipo: archivo.tipo!
                });
              },
              error: (err) => console.error('Error obteniendo archivo', err)
            });
          }
        });
      },
      error: (err) => console.error('Error cargando noticias', err)
    });
  }

  get noticiasAsLineas(): NoticiaConPlank[] {
    return this.noticias.map(n => {
      const archivo = this.archivoInfo.get(n.id!);
      let texto = n.descripcion ?? 'Sin descripción';

      if (n.url) {
        texto += ` (<a href="${n.url}" target="_blank" rel="noopener noreferrer" style="text-decoration: underline; color: var(--forest-green); cursor: pointer;">Enlace</a>)`;
      }

      if (archivo?.nombre) {
        const enlaceArchivo = `<a class="archivo-link" data-id="${n.id}" style="text-decoration: underline; color: var(--forest-green); cursor: pointer;">${archivo.nombre}</a>`;
        texto += ` (${enlaceArchivo})`;
      }

      return {
        ...n,
        texto: this.sanitizer.bypassSecurityTrustHtml(texto) as unknown as string,
        fechaCreacion: new Date().toISOString(),
        tipoDestinatario: 'alumno'
      };
    });
  }

  addNoticia(): void {
    const dialogRef = this.dialog.open(AddContentDialogComponent, {
      data: {
        fixedTipo: 'Noticia',
        hideFechaHora: true
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result?.descripcion) {
        const url = result.url || undefined;

        if (url && !this.isValidUrl(url)) {
          alert('La URL introducida no es válida. Asegúrate de que empiece por http:// o https://');
          return;
        }

        const file = result.documentFile || undefined;

        this.noticiasService.createNoticia(result.descripcion, url, file).subscribe({
          next: noticia => {
            this.noticias.push(noticia);

            if (noticia.archivoId != null) {
              this.archivosService.getArchivoInfo(noticia.archivoId).subscribe({
                next: (archivo) => {
                  this.archivoInfo.set(noticia.id!, {
                    nombre: archivo.nombre!,
                    tipo: archivo.tipo!
                  });
                },
                error: err => console.error('Error obteniendo archivo recién creado', err)
              });
            }
          },
          error: err => console.error('Error creando noticia', err)
        });

      }
    });
  }

  private isValidUrl(url: string): boolean {
    try {
      const parsed = new URL(url);
      return parsed.protocol === 'http:' || parsed.protocol === 'https:';
    } catch {
      return false;
    }
  }

  removeNoticia(id: number): void {
    if (!confirm('¿Estás seguro de que quieres eliminar esta noticia?')) return;

    this.noticiasService.deleteNoticia(id).subscribe({
      next: () => this.noticias = this.noticias.filter(n => n.id !== id),
      error: err => console.error('Error eliminando noticia', err)
    });
  }

  abrirArchivo(id: number): void {
    const noticia = this.noticias.find(n => n.id === id);
    if (!noticia?.archivoId) return;

    this.archivosService.downloadArchivo(noticia.archivoId, 'body').subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      window.open(url, '_blank');
    });
  }

}
