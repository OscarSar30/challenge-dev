import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-window',
  templateUrl: './window.component.html',
  styleUrls: ['./window.component.css']
})
export class WindowComponent implements OnInit {
  token: string = '';  
  tokenLifetime: number = 60;
  tokenInterval: any;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.getToken();
    setInterval(() => this.getToken(), 60000);
  }

  getToken() {
    this.http.get<any>('http://localhost:8099/tokens/generate?userId=ca099c5a-e44d-46e6-bc20-239e0166f1a6').subscribe(data => {
      this.token = data.token;
      this.tokenLifetime = 60;  

      if (this.tokenInterval) {
        clearInterval(this.tokenInterval);
      }

      this.tokenInterval = setInterval(() => {
        if (this.tokenLifetime > 0) {
          this.tokenLifetime--;
        } else {
          clearInterval(this.tokenInterval);
        }
      }, 1000);
    });
  }

  useToken() {
    this.http.post<any>(`http://localhost:8099/tokens/use?userId=ca099c5a-e44d-46e6-bc20-239e0166f1a6&token=${this.token}`, {}).subscribe(response => {
      if (response.success) {
        alert('Token usado correctamente');
        this.token = '';
        this.tokenLifetime = 0;
      } else {
        alert('Error al usar el token');
      }
    }, error => {
      alert('Hubo un error al intentar usar el token');
    });
  }

}
