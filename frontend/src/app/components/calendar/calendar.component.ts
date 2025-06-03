import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { Reunion } from '../../api/model/reunion';
import {
  format,
  startOfMonth,
  endOfMonth,
  getDate,
  getDaysInMonth,
  addMonths,
  subMonths,
  isSameMonth,
  isSameDay,
  parseISO
} from 'date-fns';
import { es } from 'date-fns/locale';
import {UiButtonComponent} from '../ui-button/ui-button.component';

@Component({
  selector: 'app-calendar',
  standalone: true,
  imports: [CommonModule, MatIcon, UiButtonComponent],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css'
})
export class CalendarComponent {
  @Input() reuniones: Reunion[] = [];

  currentDate: Date = new Date();
  today: Date = new Date();
  daysOfWeek = ['L', 'M', 'X', 'J', 'V', 'S', 'D'];
  selectedReunion: Reunion | null = null;

  get currentMonthLabel(): string {
    return format(this.currentDate, 'LLLL, yyyy', { locale: es }).toUpperCase();
  }

  get daysInMonth(): number[] {
    const count = getDaysInMonth(this.currentDate);
    return Array.from({ length: count }, (_, i) => i + 1);
  }

  getReunionesByDay(day: number): Reunion[] {
    return this.reuniones.filter(r => {
      const fecha = new Date(Number(r.fechaHora) * 1000);
      return isSameMonth(fecha, this.currentDate) && getDate(fecha) === day;
    });
  }

  previousMonth() {
    this.currentDate = subMonths(this.currentDate, 1);
  }

  nextMonth() {
    this.currentDate = addMonths(this.currentDate, 1);
  }

  onReunionClick(reunion: Reunion) {
    this.selectedReunion = reunion;
  }

  isToday(day: number): boolean {
    return (
      getDate(this.today) === day &&
      isSameMonth(this.currentDate, this.today)
    );
  }
}
