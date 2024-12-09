// src/app/register/register.component.ts
import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  formData = {
    username: '',
    email: '',
    password: ''
  };

  constructor(private http: HttpClient) {}

  onSubmit() {
    const apiUrl = 'http://localhost:8080/api/register'; // Replace with your API endpoint
    this.http.post(apiUrl, this.formData).subscribe(
      response => {
        console.log('Registration successful:', response);
        alert('Registration successful! Redirecting to login...');
        // Redirect to login or another page if needed
      },
      error => {
        console.error('Registration failed:', error);
        alert('Registration failed. Please try again.');
      }
    );
  }
}
