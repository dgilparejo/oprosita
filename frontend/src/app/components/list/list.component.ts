import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgForOf} from '@angular/common';
import {UiItemComponent} from '../ui-item/ui-item.component';

@Component({
  selector: 'app-list',
  standalone: true,
  imports: [NgForOf, UiItemComponent],
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent {
  @Input() items: string[] = [];
  @Output() itemClicked = new EventEmitter<string>();

  emitClick(item: string) {
    this.itemClicked.emit(item);
  }
}
