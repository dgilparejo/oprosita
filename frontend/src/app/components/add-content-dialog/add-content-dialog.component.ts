import {Component, Inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';

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
    FormsModule
  ],
  templateUrl: './add-content-dialog.component.html',
  styleUrls: ['./add-content-dialog.component.css']
})
export class AddContentDialogComponent {
  data = {
    tipo: '',
    descripcion: '',
    documentFile: null as File | null
  };

  fixedTipo: string | null = null;

  constructor(
    private dialogRef: MatDialogRef<AddContentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public injectedData: { fixedTipo?: string } // <-- nuevo
  ) {
    if (injectedData?.fixedTipo) {
      this.fixedTipo = injectedData.fixedTipo;
      this.data.tipo = this.fixedTipo;
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
      this.dialogRef.close(this.data);
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
