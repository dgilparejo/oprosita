import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { Reunion } from '../../api/model/reunion';
import { ReunionesService } from '../../api';
import { inject } from '@angular/core';
import {
  format,
  getDate,
  getDaysInMonth,
  addMonths,
  subMonths,
  isSameMonth
} from 'date-fns';
import { es } from 'date-fns/locale';
import {UiButtonComponent} from '../ui-button/ui-button.component';
import {KeycloakService} from '../../services/keycloak.service';

@Component({
  selector: 'app-calendar',
  standalone: true,
  imports: [CommonModule, MatIcon, UiButtonComponent],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css'
})
export class CalendarComponent implements OnChanges {
  @Input() reuniones: Reunion[] = [];

  currentDate: Date = new Date();
  today: Date = new Date();
  daysOfWeek = ['L', 'M', 'X', 'J', 'V', 'S', 'D'];
  selectedReunion: Reunion | null = null;
  readonly keycloakService = inject(KeycloakService);

  readonly reunionesService = inject(ReunionesService);

  ngOnChanges(changes: SimpleChanges) {
    if (changes['reuniones']) {
      console.log('Reuniones actualizadas:', this.reuniones);
    }
  }

  get esProfesor(): boolean {
    return this.keycloakService.hasRole('profesor');
  }

  get currentMonthLabel(): string {
    return format(this.currentDate, 'LLLL, yyyy', { locale: es }).toUpperCase();
  }

  get daysInMonth(): number[] {
    const count = getDaysInMonth(this.currentDate);
    return Array.from({ length: count }, (_, i) => i + 1);
  }

  getReunionesByDay(day: number): Reunion[] {
    return this.reuniones.filter(r => {
      const fecha = new Date(r.fechaHora)
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
    if (this.selectedReunion?.id === reunion.id) {
      this.selectedReunion = null;
    } else {
      this.selectedReunion = reunion;
    }
  }

  isToday(day: number): boolean {
    return (
      getDate(this.today) === day &&
      isSameMonth(this.currentDate, this.today)
    );
  }

  eliminarReunion(): void {
    if (!this.selectedReunion?.id) return;

    this.reunionesService.deleteReunion(this.selectedReunion.id).subscribe({
      next: () => {
        this.reuniones = this.reuniones.filter(r => r.id !== this.selectedReunion?.id);
        this.selectedReunion = null;
      },
      error: err => console.error('Error al eliminar la reunión', err)
    });
  }

}
