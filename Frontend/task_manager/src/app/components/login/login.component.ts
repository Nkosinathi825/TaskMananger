// login.component.ts
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../auth.service'; // Adjust the import path as necessary

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterModule, HttpClientModule], 
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  credentials = {
    email: '',
    password: ''
  };

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) {}

  onLogin() {
    const apiUrl = 'http://localhost:8080/api/auth/login'; 
    this.http.post(apiUrl, this.credentials).subscribe(
      (response: any) => {
        console.log('Login successful:', response);
        alert('Login successful!');
        
        // Use AuthService to manage user state
        this.authService.login(response);

        // Redirect to the tasks page
        this.router.navigate(['/todolist']);
      },
      error => {
        console.error('Login failed:', error);
        alert('Invalid email or password. Please try again.');
      }
    );
  }
}
