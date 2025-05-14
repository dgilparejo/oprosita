import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'app-calendar',
  standalone: true,
  imports: [CommonModule, MatIcon],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css'
})
export class CalendarComponent {
  currentMonth = 'MAYO, 2025';
  daysOfWeek = ['L', 'M', 'X', 'J', 'V', 'S', 'D'];
  days = Array.from({ length: 35 }, (_, i) => i + 1); // Simula un mes visualmente
}
