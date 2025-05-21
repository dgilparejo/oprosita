import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UiItemComponent } from '../../components/ui-item/ui-item.component';
import { UiButtonComponent } from '../../components/ui-button/ui-button.component';
import { PlankComponent } from '../../components/plank/plank.component';
import { MatExpansionModule } from '@angular/material/expansion';

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
  alumnos: { nombre: string; contenido: any }[] = [];

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.grupoId = this.route.snapshot.paramMap.get('id') || '';
    this.grupoNombre = this.formatearNombreGrupo(this.grupoId);

    this.alumnos = [
      {
        nombre: 'Juan',
        contenido: {
          temas: ['Tema 1', 'Tema 2'],
          programacion: ['Programación 1', 'Programación 2'],
          practico: ['Programación 1', 'Programación 2']
        }
      },
      {
        nombre: 'Ignacio',
        contenido: {
          temas: ['Tema 1'],
          programacion: ['Programación 1'],
          practico: ['Programación 2']
        }
      }
    ];
  }

  formatearNombreGrupo(id: string): string {
    const limpio = id.replace(/-/g, ' ');
    return limpio.charAt(0).toUpperCase() + limpio.slice(1);
  }
}
