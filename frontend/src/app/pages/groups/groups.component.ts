import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListComponent } from '../../components/list/list.component';
import { ActivatedRoute, Router } from '@angular/router';
import { GruposService } from '../../api/api/grupos.service';
import { UsuariosService } from '../../api/api/usuarios.service';
import { Grupo } from '../../api/model/grupo';
import { KeycloakService } from '../../services/keycloak.service';

@Component({
  selector: 'app-groups',
  standalone: true,
  imports: [CommonModule, ListComponent],
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css']
})
export class GroupsComponent implements OnInit {
  grupos: Grupo[] = [];
  origen: 'grupos' | 'alumnado' = 'grupos';

  get nombresGrupos(): string[] {
    return this.grupos.map(g => g.nombre);
  }

  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private gruposService = inject(GruposService);
  private usuariosService = inject(UsuariosService);
  private keycloakService = inject(KeycloakService);

  ngOnInit(): void {
    const currentPath = this.router.url.split('/')[1];
    this.origen = currentPath === 'alumnado' ? 'alumnado' : 'grupos';

    this.usuariosService.getMiUsuario().subscribe({
      next: (usuario) => {
        if (this.keycloakService.hasRole('profesor')) {
          this.gruposService.getGruposByProfesor(usuario.id!).subscribe({
            next: res => this.grupos = res,
            error: err => console.error('Error cargando grupos del profesor', err)
          });
        } else if (this.keycloakService.hasRole('alumno')) {
          const grupoId = (usuario as any).grupoId;
          if (!grupoId) {
            console.warn('Grupo ID no definido para el alumno');
            return;
          }
          this.gruposService.getGrupoById(grupoId).subscribe({
            next: grupo => this.grupos = [grupo],
            error: err => console.error('Error cargando grupo del alumno', err)
          });
        }
      },
      error: err => console.error('Error obteniendo usuario', err)
    });
  }

  irAMeses(nombreGrupo: string) {
    const id = nombreGrupo.toLowerCase().replace(/\s/g, '-');
    if (this.origen === 'grupos') {
      this.router.navigate([`/grupos/${id}`]);
    } else if (this.origen === 'alumnado') {
      this.router.navigate([`/alumnado/${id}`]);
    }
  }
}
