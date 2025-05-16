import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListComponent } from '../../components/list/list.component';
import {Router} from '@angular/router';

@Component({
  selector: 'app-groups',
  standalone: true,
  imports: [CommonModule, ListComponent],
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css']
})
export class GroupsComponent {
  grupos = ['Grupo 1', 'Grupo 2', 'Grupo 3'];

  constructor(private router: Router) {}

  irAMeses(nombreGrupo: string) {
    const id = nombreGrupo.toLowerCase().replace(/\s/g, '-');
    this.router.navigate([`/grupos/${id}`]);
  }
}
