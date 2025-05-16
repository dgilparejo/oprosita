import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ui-item',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ui-item.component.html',
  styleUrls: ['./ui-item.component.css']
})
export class UiItemComponent {
  @Input() label: string = '';
  @Output() clicked = new EventEmitter<void>();

  onClick() {
    this.clicked.emit();
  }
}
