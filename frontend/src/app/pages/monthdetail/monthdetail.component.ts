import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PlankComponent } from '../../components/plank/plank.component';
import { UiButtonComponent } from '../../components/ui-button/ui-button.component';
import { UiItemComponent } from '../../components/ui-item/ui-item.component';
import { AddContentDialogComponent } from '../../components/add-content-dialog/add-content-dialog.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import {Novedad} from '../../api';
import TipoDestinatarioEnum = Novedad.TipoDestinatarioEnum;

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
  mes = '';
  private route = inject(ActivatedRoute);

  // Datos simulados
  contenido = {
    temas: ['Tema 1', 'Tema 2'],
    programacion: ['Programación 1', 'Programación 2'],
    practico: ['Práctico 1', 'Práctico 2']
  };

  constructor(private dialog: MatDialog) {}

  addContent() {
    const dialogRef = this.dialog.open(AddContentDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Contenido añadido:', result);
        // Aquí puedes hacer push a la lista correcta, según el tipo:
        if (this.contenido[result.tipo as keyof typeof this.contenido]) {
          this.contenido[result.tipo as keyof typeof this.contenido].push(result.descripcion);
        }
        // También puedes guardar el archivo PDF en backend cuando lo tengas.
      }
    });
  }

  get temasAsNovedades(): Novedad[] {
    return this.contenido.temas.map((t, index) => ({
      id: index,
      texto: t,
      fechaCreacion: new Date().toISOString(),
      tipoDestinatario: TipoDestinatarioEnum.Alumno
    }));
  }

  get programacionAsNovedades(): Novedad[] {
    return this.contenido.programacion.map((p, index) => ({
      id: index,
      texto: p,
      fechaCreacion: new Date().toISOString(),
      tipoDestinatario: TipoDestinatarioEnum.Alumno
    }));
  }

  get practicoAsNovedades(): Novedad[] {
    return this.contenido.practico.map((p, index) => ({
      id: index,
      texto: p,
      fechaCreacion: new Date().toISOString(),
      tipoDestinatario: TipoDestinatarioEnum.Alumno
    }));
  }

  ngOnInit(): void {
    this.grupoId = this.route.snapshot.paramMap.get('id') || '';
    this.mes = this.route.snapshot.paramMap.get('mes') || '';
    console.log('Contenido simulado:', this.contenido);
  }
}
