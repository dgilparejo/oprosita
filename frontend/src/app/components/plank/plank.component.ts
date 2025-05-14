import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgFor, NgIf } from '@angular/common';
import {UiButtonComponent} from '../ui-button/ui-button.component';

@Component({
  selector: 'app-plank',
  standalone: true,
  imports: [CommonModule, FormsModule, NgFor, NgIf, UiButtonComponent],
  templateUrl: './plank.component.html',
  styleUrl: './plank.component.css'
})
export class PlankComponent {
  @Input() lines: string[] = [];
  @Input() title = '';
  @Input() editable = false;

  @Output() add = new EventEmitter<string>();

  newLine = '';

  submit() {
    if (this.newLine.trim()) {
      this.add.emit(this.newLine.trim());
      this.newLine = '';
    }
  }
  delete(index: number) {
    this.lines.splice(index, 1);
  }
}
