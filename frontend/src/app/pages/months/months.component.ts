import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ListComponent } from '../../components/list/list.component';

@Component({
  selector: 'app-months',
  standalone: true,
  imports: [CommonModule, ListComponent],
  templateUrl: './months.component.html',
  styleUrl: './months.component.css'
})
export class MonthsComponent implements OnInit {
  meses: string[] = [];
  grupoId: string = '';

  constructor(private route: ActivatedRoute) {}

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
}
