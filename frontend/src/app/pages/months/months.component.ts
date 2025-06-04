import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import { ListComponent } from '../../components/list/list.component';
import {GruposService, Mes} from '../../api';

@Component({
  selector: 'app-months',
  standalone: true,
  imports: [CommonModule, ListComponent,],
  templateUrl: './months.component.html',
  styleUrls: ['./months.component.css']
})
export class MonthsComponent implements OnInit {
  meses: Mes[] = [];
  grupoId: string = '';
  nombresMeses: string[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private gruposService: GruposService
  ) {}

  ngOnInit(): void {
    const nombreGrupo = this.route.snapshot.paramMap.get('id');
    if (!nombreGrupo) {
      console.error('No se proporcionó el nombre del grupo');
      return;
    }

    this.gruposService.getGrupos().subscribe({
      next: grupos => {
        const grupo = grupos.find(g =>
          g.nombre.toLowerCase().replace(/\s/g, '-') === nombreGrupo
        );
        if (!grupo) {
          console.warn('No se encontró un grupo con ese nombre');
          return;
        }

        this.grupoId = grupo.id!.toString();

        this.gruposService.getMesesByGrupo(grupo.id!).subscribe({
          next: (meses: Mes[]) => {
            this.meses = meses;
            this.nombresMeses = meses.map(m => m.nombre);
          },
          error: (err) => {
            console.error('Error al cargar los meses del grupo:', err);
          }
        });
      },
      error: err => console.error('Error al obtener grupos:', err)
    });

  }

  navegarAMes(nombre: string) {
    const mes = this.meses.find(m => m.nombre === nombre);
    const nombreGrupo = this.route.snapshot.paramMap.get('id');
    if (mes && nombreGrupo) {
      this.router.navigate([`/grupos/${nombreGrupo}/${nombre}`]);
    } else {
      console.error('No se pudo encontrar el mes o el nombre del grupo.');
    }
  }
}

