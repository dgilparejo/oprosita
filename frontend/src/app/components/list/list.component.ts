import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-list',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './list.component.html',
  styleUrl: './list.component.css'
})
export class ListComponent {
  @Input() items: string[] = [];
  @Output() itemClicked = new EventEmitter<string>();

  emitClick(item: string) {
    this.itemClicked.emit(item);
  }

}
