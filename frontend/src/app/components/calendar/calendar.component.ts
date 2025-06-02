import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { Reunion } from '../../api/model/reunion';

@Component({
  selector: 'app-calendar',
  standalone: true,
  imports: [CommonModule, MatIcon],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css'
})
export class CalendarComponent {
  @Input() reuniones: Reunion[] = [];

  currentMonth = 'JUNIO, 2025';
  daysOfWeek = ['L', 'M', 'X', 'J', 'V', 'S', 'D'];
  days = Array.from({ length: 30 }, (_, i) => i + 1);

  getReunionesByDay(day: number): Reunion[] {
    return this.reuniones.filter(r => new Date(r.fechaHora).getDate() === day);
  }
}
