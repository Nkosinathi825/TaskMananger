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
  selector: 'app-tasks',
  standalone: true,
  imports: [FormsModule, HttpClientModule, RouterModule, CommonModule],
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements OnInit {
  data: any;
  tasks: Task[] = [];
  completedTasks: Task[] = [];
  notCompletedTasks: Task[] = [];
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

  logout(): void {
    sessionStorage.clear()

    
    this.router.navigate(['/login'])
  }
  
  switch(): void {

    this.router.navigate(['/list']);
  }


  fetchTodos(userId: number): void {
    this.http.get<Task[]>(`http://localhost:8080/api/todos/${userId}`).subscribe(
      (response) => {
        this.tasks = response;
        this.categorizeTasks(); 
      },
      (error) => {
        console.error('Error fetching tasks:', error);
        alert('Failed to load tasks. Please try again.');
      }
    );
  }

  categorizeTasks(): void {
    this.completedTasks = this.tasks.filter(task => task.completed);
    this.notCompletedTasks = this.tasks.filter(task => !task.completed);
  }

  addTask(): void {
    if (this.formData.title && this.formData.description && this.formData.dueDate) {
      if (this.isDateDue(this.formData.dueDate)) {
        alert('The due date cannot be in the past.');
        return;
      }

      const newTask: Task = { ...this.formData, completed: false };
      this.http.post<Task>(`http://localhost:8080/api/todos/${this.data.id}`, newTask).subscribe(
        (response) => {
          this.tasks.push(response);
          this.categorizeTasks(); // Update lists after adding a task
          this.resetForm();
        },
        (error) => {
          console.error('Error adding task:', error);
          alert('Failed to add task. Please try again.');
        }
      );
    } else {
      alert('Please fill in all fields.');
    }
  }

  deleteTask(index: number, listType: 'completed' | 'notCompleted'): void {
    const taskList = listType === 'completed' ? this.completedTasks : this.notCompletedTasks;
    const taskId = taskList[index]?.id;
  
    if (taskId) {
      this.http.delete(`http://localhost:8080/api/todos/${taskId}`).subscribe(
        () => {
          // Remove from the respective list
          taskList.splice(index, 1);
          // Update the master list
          this.tasks = this.tasks.filter(task => task.id !== taskId);
        },
        (error) => {
          console.error('Error deleting task:', error);
          alert('Failed to delete task. Please try again.');
        }
      );
    }
  }
  

  toggleCompletion(index: number, listType: 'completed' | 'notCompleted'): void {
    const taskList = listType === 'completed' ? this.completedTasks : this.notCompletedTasks;
    const task = taskList[index];
    
    if (task) {
      task.completed = !task.completed;
  
      this.http.put<Task>(`http://localhost:8080/api/todos/${task.id}`, task).subscribe(
        (response: Task) => {
          // Remove from current list and add to the other
          taskList.splice(index, 1);
          if (task.completed) {
            this.completedTasks.push(task);
          } else {
            this.notCompletedTasks.push(task);
          }
          // Update the master task list
          const idx = this.tasks.findIndex(t => t.id === task.id);
          if (idx !== -1) {
            this.tasks[idx] = task;
          }
        },
        (error) => {
          console.error('Error toggling task completion:', error);
          alert('Failed to toggle task completion. Please try again.');
        }
      );
    }
  }
  

  editTask(index: number, listType: 'completed' | 'notCompleted'): void {
    const taskList = listType === 'completed' ? this.completedTasks : this.notCompletedTasks;
    const task = taskList[index];
    
    if (task) {
      this.formData = { ...task };
      this.editingTaskId = task.id ?? null;
    }
  }
  

  updateTask(): void {
    if (this.editingTaskId !== null) {
      const updatedTask: Task = { ...this.formData };
  
      this.http.put<Task>(`http://localhost:8080/api/todos/${this.editingTaskId}`, updatedTask).subscribe(
        (response: Task) => {
          // Update the respective task in `tasks` and recategorize
          const idx = this.tasks.findIndex(t => t.id === this.editingTaskId);
          if (idx !== -1) {
            this.tasks[idx] = response;
          }
          this.categorizeTasks(); // Reorganize tasks into their lists
          this.cancelEditing();
        },
        (error) => {
          console.error('Error updating task:', error);
          alert('Failed to update task. Please try again.');
        }
      );
    }
  }
  

  isDateDue(dueDate: string): boolean {
    const currentDate = new Date();
    const selectedDate = new Date(dueDate);
    return selectedDate < currentDate;
  }

  cancelEditing(): void {
    this.editingTaskId = null;
    this.resetForm();
  }

  private resetForm(): void {
    this.formData = { title: '', description: '', dueDate: '', completed: false };
  }
}
