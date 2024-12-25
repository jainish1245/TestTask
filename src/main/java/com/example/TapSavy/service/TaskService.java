package com.example.TapSavy.service;

import com.example.TapSavy.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> getAllTask();

    Optional<Task> getTaskById(Long id);

    Task createTask(Task task);

    Optional<Task> updateTask(Long id, Task updatedTask);

    Optional<Task> updateTaskStatus(Long id, String status);

    Boolean deleteTask(Long id);

}
