import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { WindowComponent } from '../window/window.component';

@Component({
  selector: 'app-button',
  templateUrl: '/button.component.html',
  standalone: false,
  styleUrl: './button.component.css'
})
export class ButtonComponent {
  constructor(private dialog: MatDialog) {}

  openWindow() {
    this.dialog.open(WindowComponent);
  }
}