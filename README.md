# TaskMananger


READ ME :


How to setup and run the application:

1.clone the repo to your pc (i used visual studio code for this whole process if you are using any other ide then i cant help)

2.change your directory to the application folder , the are two folders inside the Backend folder and the Frontend folder 

3.change directory to the backend folder again  change you directory to the inner file inside the backend folder called "task_manager"(since i was instructed that i should use spring boot i will assume that you have java already running on your machine and all the necessary requirements to run spring boot ) and run this command "mvn spring-boot:run " im using maven if this comand fails in your pc (your properly dont have maven or java install dependig on the error install the appropriate packages)

4.now change directory to the frontend again  change you directory to the inner file inside the frontend folder called "task_manager" (since i was instructed to use angular i will assume you have all the necessary tools to run this application meaning you already have npm installed and also  the angular cli installed ) once in this folder run "npm install" wait till is done then  run this command to start the application "npm start" note :(by all means make sure that your frontend app is running on  port 4200 otherwise it wont be able to connect with the backend as this  will raise a cors error if you have  anything running on port 4200 you have to stop it) .


After the above process  the application should be running 


This is a Task Manager application :

The user is allowed to register ,Login, and manager tasks,also logout:

Task Management :
1.The user can create a new task  by providing the necessary details and click "add task"  then the task will automatically move to the "To do task" side 

2.The user is able to complete(or rather mark as complete) the task by clicking  "complete" then the task will automatically move to the completed side, 

3.The user is able to edit a task by clicking "edit" and the task will be move to the inputs then the use can edit the task and the publish the task again by clicking "update" if the use mistakenly clicks add task instead of update a new task will be created and show but if the page is refreshed then everything will be fine the task will be updated (  :) dont click add task when modify the task)

4.The user can view all tasks which will be sorted by due date by clicking "view all task"
5.A user cant choose an outdated due date  note :( this is only a frontend feature not a backend feature meaning if u were to just use postman to enter a task with an outdated due date it is going to work  i only did this in the frontend so that there is no useless calling of an api)
5.the user can nicely logout if want to 


The following are the request made to the apis and their reponse (to test the api i used a tool in vscode called Rest Client):

1.The request for registering and the response:

POST  http://localhost:8080/api/auth/register
Content-Type: application/json

{
    "username":"Nkosinathi",
    "email":"nkosinathi@example.com",
    "password":"123"
    
}

HTTP/1.1 200 


{
  "id": 6,
  "username": "Nkosinathi",
  "password": "$2a$10$U6Zqu7DxXH6.gz.jKNsi2.59fThOusyh3t.jZVYl6uqEicRDth3Ju",
  "email": "nkosinathi@example.com"
}

2.The request for login and the response:

POST  http://localhost:8080/api/auth/login
Content-Type: application/json

{
    
    "email":"nkosinathi@example.com",
    "password":"123"
    
}
HTTP/1.1 200 


{
  "id": 6,
  "username": "Nkosinathi",
  "password": "$2a$10$U6Zqu7DxXH6.gz.jKNsi2.59fThOusyh3t.jZVYl6uqEicRDth3Ju",
  "email": "nkosinathi@example.com"
}

3.The request and the response for adding a task or saving a task:
the api is like this : http://localhost:8080/api/todos/{user_id} 

POST  http://localhost:8080/api/todos/6
Content-Type: application/json

{
    "title": "Complete",
    "description": "Finish the task manager project",
    "dueDate": "2024-12-09",
    "completed": false
    
}

HTTP/1.1 200 


{
  "id": 41,
  "title": "Complete",
  "description": "Finish the task manager project",
  "dueDate": "2024-12-09",
  "completed": false,
  "user": {
    "id": 6,
    "username": "Nkosinathi",
    "password": "$2a$10$U6Zqu7DxXH6.gz.jKNsi2.59fThOusyh3t.jZVYl6uqEicRDth3Ju",
    "email": "nkosinathi@example.com"
  }
}

4.The request and response for  fetching a task :
the api is like this : http://localhost:8080/api/todos/{user_id} 

GET  http://localhost:8080/api/todos/6
Content-Type: application/json


HTTP/1.1 200 


[
  {
    "id": 41,
    "title": "Complete",
    "description": "Finish the task manager project",
    "dueDate": "2024-12-09",
    "completed": false,
    "user": {
      "id": 6,
      "username": "Nkosinathi",
      "password": "$2a$10$U6Zqu7DxXH6.gz.jKNsi2.59fThOusyh3t.jZVYl6uqEicRDth3Ju",
      "email": "nkosinathi@example.com"
    }
  },
  {
    "id": 42,
    "title": "FIRST ",
    "description": "Finish the task manager project",
    "dueDate": "2024-12-09",
    "completed": false,
    "user": {
      "id": 6,
      "username": "Nkosinathi",
      "password": "$2a$10$U6Zqu7DxXH6.gz.jKNsi2.59fThOusyh3t.jZVYl6uqEicRDth3Ju",
      "email": "nkosinathi@example.com"
    }
  }
]

5.The request and response for deleting a tasks:

the api is like this : http://localhost:8080/api/todos/{todo_id} 


DELETE   http://localhost:8080/api/todos/42
Content-Type: application/json


HTTP/1.1 200 

6.The request to modify or edit data:

both the edit and the mark as complete features use the same api and the logic is the same "overwrite the old todo information with the new one"


the api is like this : http://localhost:8080/api/todos/{todo_id} 

PUT  http://localhost:8080/api/todos/41
Content-Type: application/json

{
    "title": "Second",
    "description": "Finish the task manager project",
    "dueDate": "2024-12-09",
    "completed": true
    
}

HTTP/1.1 200 

{
  "id": 41,
  "title": "Second",
  "description": "Finish the task manager project",
  "dueDate": "2024-12-09",
  "completed": true,
  "user": {
    "id": 6,
    "username": "Nkosinathi",
    "password": "$2a$10$U6Zqu7DxXH6.gz.jKNsi2.59fThOusyh3t.jZVYl6uqEicRDth3Ju",
    "email": "nkosinathi@example.com"
  }
}

:( note deleting or modify a deleted tasks will result in a error these error wont happen in the frontend when using the application but be careful they can occure when just testing the apis using postman or Rest Client or any other similar tool



Side notes:

1.I didn't really understand the exposing apis concept but instead i didn't use tokens so the is no validation needed for sending request to using the apis and i also pertmitAll access for all the apis hope that is what they meant by exposing an api


2.my application is not fully localized as i used an online database called Neon PostgreSQL this is because there is no other tool that is local that i know that can have the database if the is a need to see the database please use this url (https://console.neon.tech/realms/prod-realm/protocol/openid-connect/auth?client_id=neon-console&redirect_uri=https%3A%2F%2Fconsole.neon.tech%2Fauth%2Fkeycloak%2Fcallback&response_type=code&scope=openid+profile+email&state=-VEwpS5wQAoTbVrCCCzLsA%3D%3D%2C%2C%2C&ref=/app/projects)to login use the following credentials {email=076640m@gmail.com password=Task@123$} it is a dummy account for this assessment 

3.

