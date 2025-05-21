import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UiItemComponent } from '../../components/ui-item/ui-item.component';
import { UiButtonComponent } from '../../components/ui-button/ui-button.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AddContentDialogComponent } from '../../components/add-content-dialog/add-content-dialog.component';

@Component({
  selector: 'app-news',
  standalone: true,
  imports: [
    CommonModule,
    UiItemComponent,
    UiButtonComponent,
    MatDialogModule
  ],
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {
  noticias: string[] = [
    'Convocatoria oposiciones CyL.',
    'Nueva Orden 25/366 de Evaluación.'
  ];

  constructor(private dialog: MatDialog) {}

  addNoticia() {
    const dialogRef = this.dialog.open(AddContentDialogComponent, {
      data: { fixedTipo: 'Noticia' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result?.descripcion) {
        this.noticias.push(result.descripcion);
      }
    });
  }

  ngOnInit(): void {
    console.log('Noticias:', this.noticias);
  }
}
