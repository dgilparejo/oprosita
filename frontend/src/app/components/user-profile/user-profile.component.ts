import { Component } from '@angular/core';
import {MatMenu, MatMenuTrigger} from '@angular/material/menu';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [
    MatMenu,
    MatIcon,
    MatMenuTrigger
  ],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent {
  fullName = 'Alberto Muñoz';

  get initials(): string {
    const names = this.fullName.split(' ');
    return names.map(n => n[0]).join('').toUpperCase();
  }

  logout() {
    // Simulación de logout
    alert('Sesión cerrada');
  }
}
