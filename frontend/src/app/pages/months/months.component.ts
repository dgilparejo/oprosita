import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import { ListComponent } from '../../components/list/list.component';

@Component({
  selector: 'app-months',
  standalone: true,
  imports: [CommonModule, ListComponent,],
  templateUrl: './months.component.html',
  styleUrls: ['./months.component.css']
})
export class MonthsComponent implements OnInit {
  meses: string[] = [];
  grupoId: string = '';

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.grupoId = this.route.snapshot.paramMap.get('id') || '';

    this.meses = [
      'Septiembre',
      'Octubre',
      'Noviembre',
      'Diciembre',
      'Enero',
      'Febrero',
      'Marzo',
      'Abril',
      'Mayo',
      'Junio'
    ];
  }
  navegarAMes(mes: string) {
    // Navegar al detalle del mes usando el ID del grupo y el mes
    const mesParam = mes.toLowerCase().replace(/\s/g, '-');
    this.router.navigate([`/grupos/${this.grupoId}/${mesParam}`]);
  }
}
