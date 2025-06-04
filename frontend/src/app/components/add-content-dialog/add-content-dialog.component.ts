import {Component, Inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {Grupo} from '../../api';

@Component({
  selector: 'app-add-content-dialog',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  templateUrl: './add-content-dialog.component.html',
  styleUrls: ['./add-content-dialog.component.css']
})
export class AddContentDialogComponent {
  data = {
    tipo: '',
    descripcion: '',
    url: '',
    documentFile: null as File | null,
    fechaHora: null as Date | null,
    hora: ''
  };

  fixedTipo: string | null = null;
  minDate: Date = new Date();
  hideFileUpload = false;
  gruposDisponibles: Grupo[] = [];
  grupoIdSeleccionado: number | null = null;

  constructor(
    private dialogRef: MatDialogRef<AddContentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public injectedData: {
      fixedTipo?: string,
      hideFileUpload?: boolean,
      gruposDisponibles?: Grupo[]
    }
  ) {
    if (injectedData?.fixedTipo) {
      this.fixedTipo = injectedData.fixedTipo;
      this.data.tipo = this.fixedTipo;
    }

    if (injectedData?.hideFileUpload) {
      this.hideFileUpload = true;
    }

    if (injectedData?.gruposDisponibles) {
      this.gruposDisponibles = injectedData.gruposDisponibles;
      if (this.gruposDisponibles.length === 1) {
        this.grupoIdSeleccionado = this.gruposDisponibles[0].id ?? null;
      }
    }
  }

  isValid(): boolean {
    return this.data.tipo !== '' && this.data.descripcion.trim() !== '';
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.data.documentFile = input.files[0];
    }
  }

  onSubmit(): void {
    if (this.isValid()) {
      if (this.data.fechaHora && this.data.hora) {
        const [hours, minutes] = this.data.hora.split(':').map(Number);
        this.data.fechaHora.setHours(hours, minutes, 0, 0);
      }

      const payload = {
        ...this.data,
        grupoId: this.grupoIdSeleccionado
      };

      console.log('🟢 Datos enviados desde el diálogo:', payload);

      this.dialogRef.close(payload);
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
