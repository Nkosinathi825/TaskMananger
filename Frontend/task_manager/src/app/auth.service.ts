import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private user = new BehaviorSubject<any>(null); 
  private authenticated = new BehaviorSubject<boolean>(false);

  constructor() {
    const userData = sessionStorage.getItem('userData');
    if (userData) {
      this.authenticated.next(true); 
      this.user.next(JSON.parse(userData));
    }
  }

  login(userData: any) {
    sessionStorage.setItem('userData', JSON.stringify(userData)); 
    this.authenticated.next(true); 
    this.user.next(userData); 
  }

  logout() {
    sessionStorage.removeItem('userData');
    this.authenticated.next(false);
    this.user.next(null);
  }

  isAuthenticated() {
    return this.authenticated.asObservable(); 
  }

  getUser() {
    return this.user.asObservable(); 
  }
}
