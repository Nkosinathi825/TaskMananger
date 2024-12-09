import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private user = new BehaviorSubject<any>(null); 
  private authenticated = new BehaviorSubject<boolean>(false); 

  constructor() {
  }

  login(userData: any) {
    this.authenticated.next(true); 
    this.user.next(userData); 
  }

  logout() {
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
