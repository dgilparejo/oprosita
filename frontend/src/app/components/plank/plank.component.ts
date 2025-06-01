import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgFor, NgIf } from '@angular/common';
import { UiButtonComponent } from '../ui-button/ui-button.component';
import {Novedad} from '../../api';

@Component({
  selector: 'app-plank',
  standalone: true,
  imports: [CommonModule, FormsModule, NgFor, NgIf, UiButtonComponent],
  templateUrl: './plank.component.html',
  styleUrl: './plank.component.css'
})
export class PlankComponent {
  @Input() lines: Novedad[] = [];
  @Input() title = '';
  @Input() editable = false;
  @Input() allowDeleteOnly = false;

  @Output() add = new EventEmitter<string>();
  @Output() remove = new EventEmitter<number>();

  newLine = '';

  submit() {
    if (this.newLine.trim()) {
      this.add.emit(this.newLine.trim());
      this.newLine = '';
    }
  }

  delete(index: number) {
    const id = this.lines[index]?.id;
    if (id != null) {
      this.remove.emit(id);
    }
  }
}
