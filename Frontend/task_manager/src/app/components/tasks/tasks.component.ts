import { Component } from '@angular/core';
import { AuthService } from '../../auth.service';


@Component({
  selector: 'app-tasks',
  imports: [],
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.scss'
})
export class TasksComponent {
  data: any;

  constructor(private authService: AuthService) {
    this.authService.getUser().subscribe(data => {
      this.data = data;
    });
  }
}

  