# TestTask
Create a restful Apis for task also integrate tredis and email service

Table -
id, email, name, description, status, date, duedate

-- Endpoints
- POST - http://localhost:8080/v1/tasks  - Create task 
- GET - http://localhost:8080/v1/tasks  - Get task 
- GET - http://localhost:8080/v1/tasks/{id}  - Get task by id
- PATCH - http://localhost:8080/v1/tasks/{id}/{status}  - Task status update on id
- DELETE - http://localhost:8080/v1/tasks/{id}  - Delete task by id
- PUT - http://localhost:8080/v1/tasks/{id}  - Update task - name, description

