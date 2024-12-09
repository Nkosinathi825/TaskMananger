import { Component } from '@angular/core';
import { AuthService } from '../../auth.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; // Import CommonModule

interface Task {
  title: string;
  description: string;
  dueDate: string;
  completed: boolean;
}

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [FormsModule, HttpClientModule, RouterModule, CommonModule], // Include CommonModule here
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent {
  data: any;
  tasks: Task[] = [
    { title: 'Sample Task 1', description: 'This is a sample task description.', dueDate: '2024-12-10', completed: false },
    { title: 'Sample Task 2', description: 'Another task description for demo purposes.', dueDate: '2024-12-11', completed: true },
    { title: 'Sample Task 3', description: 'Yet another task to display.', dueDate: '2024-12-12', completed: false }
  ]; // Predefined dummy tasks
  formData: Task = {
    title: '',
    description: '',
    dueDate: '',
    completed: false
  };

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) {
    this.authService.getUser().subscribe(data => {
      this.data = data;
    });
  }

  isDateDue(dueDate: string): boolean {
    const currentDate = new Date();
    const selectedDate = new Date(dueDate);
    if (selectedDate < currentDate) {
      return true;
    }
    return this.tasks.some(task => task.dueDate === dueDate);
  }

  addTask() {
    if (this.formData.title && this.formData.description && this.formData.dueDate) {
      if (this.isDateDue(this.formData.dueDate)) {
        alert('The due date cannot be in the past.');
        return;
      }
      this.tasks.push({ ...this.formData });
      this.resetForm();
    } else {
      alert('Please fill in all fields.');
    }
  }

  deleteTask(index: number) {
    this.tasks.splice(index, 1);
  }

  toggleCompletion(index: number) {
    this.tasks[index].completed = !this.tasks[index].completed;
  }

  editTask(index: number) {
    const task = this.tasks[index];
    this.formData = { ...task };
    this.deleteTask(index); 
  }

  private resetForm() {
    this.formData = { title: '', description: '', dueDate: '', completed: false };
  }
}
