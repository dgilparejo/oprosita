import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlankComponent } from '../../components/plank/plank.component';
import { UiButtonComponent } from '../../components/ui-button/ui-button.component';
import { UiItemComponent } from '../../components/ui-item/ui-item.component';
import { AddContentDialogComponent } from '../../components/add-content-dialog/add-content-dialog.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'app-simulation',
  standalone: true,
  imports: [
    CommonModule,
    PlankComponent,
    UiButtonComponent,
    UiItemComponent,
    MatDialogModule
  ],
  templateUrl: './simulation.component.html',
  styleUrls: ['./simulation.component.css']
})
export class SimulationComponent implements OnInit {
  simulacros = ['Simulacro 1', 'Simulacro 2'];

  constructor(private dialog: MatDialog) {}

  addSimulation() {
    const dialogRef = this.dialog.open(AddContentDialogComponent, {
      data: { fixedTipo: 'Simulacro' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Simulacro añadido:', result);
        this.simulacros.push(result.descripcion);
      }
    });
  }

  ngOnInit(): void {
    console.log('Simulacros disponibles:', this.simulacros);
  }
}
