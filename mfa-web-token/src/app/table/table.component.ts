import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  standalone: false,
  styleUrl: './table.component.css'
})
export class TableComponent implements OnInit {
  tokens: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.getTokens();
  }

  getTokens() {
    this.http.get<any[]>('http://localhost:8099/tokens?userId=ca099c5a-e44d-46e6-bc20-239e0166f1a6').subscribe(data => {
      this.tokens = data;
    });
  }
}
