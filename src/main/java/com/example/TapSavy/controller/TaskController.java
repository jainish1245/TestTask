package com.example.TapSavy.controller;

import com.example.TapSavy.entity.Task;
import com.example.TapSavy.service.TaskService;
import com.example.TapSavy.service.impl.TaskServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( value = "/v1/tasks", produces = {MediaType.APPLICATION_JSON_VALUE})
public class TaskController {

    private TaskService taskService;

    TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> allTask = taskService.getAllTask();
        return new ResponseEntity<>(allTask, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        Optional<Task> taskById = taskService.getTaskById(id);
        if (taskById.isEmpty()) {
            return new ResponseEntity<>("Task not found with this Id - "+id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taskById, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task tk = taskService.createTask(task);
        return new ResponseEntity<>(tk, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Optional<Task> task = taskService.updateTask(id, updatedTask);
        if (task.isEmpty()){
            return new ResponseEntity<>("Task not found with this Id - "+id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @PathVariable String status) {
        Optional<Task> task = taskService.updateTaskStatus(id,status);
        if (task.isEmpty()){
            return new ResponseEntity<>("Task not found with this Id - "+id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        Boolean b = taskService.deleteTask(id);
        if (b == false) {
            return new ResponseEntity<>("No Task to Delete with this Id - "+id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Task Deleted Successfully with Id - "+id, HttpStatus.ACCEPTED);
    }
}
