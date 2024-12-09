import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../auth.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

interface Task {
  id?: number; 
  title: string;
  description: string;
  dueDate: string;
  completed: boolean;
}
@Component({
  selector: 'app-list',
  standalone: true,
  imports: [FormsModule, HttpClientModule, RouterModule, CommonModule],
  templateUrl: './list.component.html',
  styleUrl: './list.component.scss'
})
export class ListComponent {
  data: any;
  tasks: Task[] = [];
  formData: Task = {
    title: '',
    description: '',
    dueDate: '',
    completed: false
  };
  editingTaskId: number | null = null;

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.authService.getUser().subscribe((data) => {
      this.data = data;
      if (data && data.id) {
        this.fetchTodos(data.id);
      } else {
        alert('User not found');
        this.router.navigate(['/login']);
      }
    });
  }

  Back(): void {
    this.router.navigate(['/todolist']); 
  }

  fetchTodos(userId: number): void {
    this.http.get<Task[]>(`http://localhost:8080/api/todos/${userId}`).subscribe(
      (response) => {
        this.tasks = response;
        console.log(this.tasks); // Check if tasks are fetched
      },
      (error) => {
        console.error('Error fetching tasks:', error);
        alert('Failed to load tasks. Please try again.');
      }
    );
  }
  


  deleteTask(index: number): void {
    const taskId = this.tasks[index].id;
    if (taskId) {
      this.http.delete(`http://localhost:8080/api/todos/${taskId}`).subscribe(
        () => {
          this.tasks.splice(index, 1);
        },
        (error) => {
          console.error('Error deleting task:', error);
          alert('Failed to delete task. Please try again.');
        }
      );
    }
  }




}
