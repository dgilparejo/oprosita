import {
  Component, Input, Output, EventEmitter, ElementRef,
  Renderer2, AfterViewChecked, OnChanges, SimpleChanges
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgFor, NgIf } from '@angular/common';
import { UiButtonComponent } from '../ui-button/ui-button.component';
import { NovedadConArchivo } from '../../model/NovedadConArchivo';

@Component({
  selector: 'app-plank',
  standalone: true,
  imports: [CommonModule, FormsModule, NgFor, NgIf, UiButtonComponent],
  templateUrl: './plank.component.html',
  styleUrl: './plank.component.css'
})
export class PlankComponent implements AfterViewChecked, OnChanges  {
  constructor(private elRef: ElementRef, private renderer: Renderer2) {}

  @Input() lines: NovedadConArchivo[] = [];
  @Input() title = '';
  @Input() editable = false;
  @Input() allowDeleteOnly = false;

  @Output() add = new EventEmitter<string>();
  @Output() remove = new EventEmitter<number>();
  @Output() clicked = new EventEmitter<number>();
  @Output() abrirArchivo = new EventEmitter<number>();

  newLine = '';
  private listeners: Array<() => void> = [];
  private initialized = false;

  submit(): void {
    if (this.newLine.trim()) {
      this.add.emit(this.newLine.trim());
      this.newLine = '';
    }
  }

  delete(index: number): void {
    const id = this.lines[index]?.id;
    if (id != null) {
      const confirmado = window.confirm('¿Estás seguro de que deseas eliminarlo?');
      if (confirmado) {
        this.remove.emit(id);
      }
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['lines']) {
      this.initialized = false;
    }
  }

  ngAfterViewChecked(): void {
    if (this.initialized) return;
    this.initialized = true;

    // Limpia listeners anteriores
    this.listeners.forEach(unlisten => unlisten());
    this.listeners = [];

    const hostElement = this.elRef.nativeElement;
    const anchors = hostElement.querySelectorAll('a.archivo-link');

    anchors.forEach((anchor: HTMLElement) => {
      const listener = this.renderer.listen(anchor, 'click', (event: Event) => {
        event.preventDefault();
        const id = Number(anchor.getAttribute('data-id'));
        this.abrirArchivo.emit(id);
      });
      this.listeners.push(listener);
    });
  }

}
