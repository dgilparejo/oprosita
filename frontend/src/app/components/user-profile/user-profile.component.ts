import { Component } from '@angular/core';
import { MatMenu, MatMenuTrigger } from '@angular/material/menu';
import { MatIcon } from '@angular/material/icon';
import { KeycloakService } from '../../services/keycloak.service'; // ajusta la ruta si es necesario

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

  constructor(private keycloakService: KeycloakService) {}

  get initials(): string {
    const username = this.keycloakService.tokenParsed?.['preferred_username'];
    return username ? username.substring(0, 2).toUpperCase() : '??';
  }

  logout(): void {
    this.keycloakService.logout();
  }
}
