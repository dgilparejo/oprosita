import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListComponent } from '../../components/list/list.component';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-groups',
  standalone: true,
  imports: [CommonModule, ListComponent],
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css']
})
export class GroupsComponent implements OnInit {
  grupos = ['Grupo 1', 'Grupo 2', 'Grupo 3'];
  origen: 'grupos' | 'alumnado' = 'grupos';

  private router = inject(Router);
  private route = inject(ActivatedRoute);

  ngOnInit(): void {
    const currentPath = this.router.url.split('/')[1];
    this.origen = currentPath === 'alumnado' ? 'alumnado' : 'grupos';
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
