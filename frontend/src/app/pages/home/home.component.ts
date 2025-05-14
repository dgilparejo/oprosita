import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlankComponent } from '../../components/plank/plank.component';
import { CalendarComponent } from '../../components/calendar/calendar.component';
import { UiButtonComponent } from '../../components/ui-button/ui-button.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    PlankComponent,
    CalendarComponent,
    UiButtonComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  novedadesProfesor = [
    'Ignacio ha escrito en su chat.',
    'David ha subido 1 programación.',
    'Juan ha subido 3 temas.'
  ];

  novedadesAlumno = [
    'Aviso: esta semana no habrá clase.',
    'Nueva publicación en noticias.'
  ];

  addNuevaNovedad(nueva: string) {
    this.novedadesAlumno.push(nueva);
  }

}
