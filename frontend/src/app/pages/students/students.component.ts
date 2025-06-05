import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UiItemComponent } from '../../components/ui-item/ui-item.component';
import { UiButtonComponent } from '../../components/ui-button/ui-button.component';
import { PlankComponent } from '../../components/plank/plank.component';
import { MatExpansionModule } from '@angular/material/expansion';
import { AlumnosService, ContenidoService, ArchivosService, Alumno, Novedad } from '../../api';
import { DomSanitizer } from '@angular/platform-browser';
import { NovedadConArchivo } from '../../model/NovedadConArchivo';

@Component({
  selector: 'app-students',
  standalone: true,
  imports: [
    CommonModule,
    UiItemComponent,
    UiButtonComponent,
    PlankComponent,
    MatExpansionModule
  ],
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})
export class StudentsComponent implements OnInit {
  grupoId = '';
  grupoNombre = '';
  alumnos: {
    id: number;
    nombre: string;
    contenido: Record<'temas' | 'programacion' | 'practico', NovedadConArchivo[]>;
  }[] = [];

  constructor(
    private route: ActivatedRoute,
    private alumnosService: AlumnosService,
    private contenidoService: ContenidoService,
    private archivosService: ArchivosService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.grupoId = this.route.snapshot.paramMap.get('id') || '';
    this.grupoNombre = this.formatearNombreGrupo(this.grupoId);

    const idNumerico = Number(this.grupoId.replace(/[^\d]/g, ''));

    this.alumnosService.getAlumnosByGrupo(idNumerico).subscribe({
      next: alumnos => {
        alumnos.forEach(alumno => {
          this.contenidoService.getContenidoPrivadoAlumno(alumno.id!).subscribe({
            next: contenidos => {
              const organizado: Record<'temas' | 'programacion' | 'practico', NovedadConArchivo[]> = {
                temas: [],
                programacion: [],
                practico: []
              };

              contenidos.forEach(item => {
                const tipo = item.tipoContenido.toLowerCase() as keyof typeof organizado;
                const novedad: NovedadConArchivo = {
                  id: item.id!,
                  archivoId: item.archivoId ?? undefined,
                  texto: item.texto,
                  fechaCreacion: new Date().toISOString(),
                  tipoDestinatario: Novedad.TipoDestinatarioEnum.Alumno
                };

                if (item.archivoId) {
                  this.archivosService.getArchivoInfo(item.archivoId).subscribe(archivo => {
                    const enlace = `<a class="archivo-link" style="text-decoration: underline; color: var(--forest-green); cursor: pointer">${archivo.nombre}</a>`;
                    novedad.texto = this.sanitizer.bypassSecurityTrustHtml(`${item.texto} (${enlace})`) as unknown as string;
                    organizado[tipo].push(novedad);
                  });
                } else {
                  novedad.texto = this.sanitizer.bypassSecurityTrustHtml(item.texto) as unknown as string;
                  organizado[tipo].push(novedad);
                }
              });

              this.alumnos.push({
                id: alumno.id!,
                nombre: alumno.nombre!,
                contenido: organizado
              });
            }
          });
        });
      },
      error: err => console.error('Error cargando alumnos', err)
    });
  }

  formatearNombreGrupo(id: string): string {
    const limpio = id.replace(/-/g, ' ');
    return limpio.charAt(0).toUpperCase() + limpio.slice(1);
  }
}
