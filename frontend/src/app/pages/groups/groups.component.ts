import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListComponent } from '../../components/list/list.component';
import { ActivatedRoute, Router } from '@angular/router';
import { GruposService } from '../../api/api/grupos.service';
import { Grupo } from '../../api/model/grupo';

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

  ngOnInit(): void {
    const currentPath = this.router.url.split('/')[1];
    this.origen = currentPath === 'alumnado' ? 'alumnado' : 'grupos';

    this.gruposService.getGrupos().subscribe({
      next: (res) => this.grupos = res,
      error: (err) => console.error('Error al obtener grupos:', err)
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
