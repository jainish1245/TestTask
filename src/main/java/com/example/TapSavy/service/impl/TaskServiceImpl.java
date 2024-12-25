package com.example.TapSavy.service.impl;

import com.example.TapSavy.entity.Task;
import com.example.TapSavy.repository.TaskRepository;
import com.example.TapSavy.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "Task")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;


    private TaskRepository taskRepository;

    TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    @CachePut(key = "'allTasks'")
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @Override
    @Cacheable(key = "'t_'+#id")
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task createTask(Task task) {
        LocalDate currentDate = LocalDate.now();
        LocalDate due = currentDate.plusDays(3);
        task.setDate(currentDate);
        task.setDueDate(due);
        task.setStatus("PENDING");
        return taskRepository.save(task);
    }

    @Override
    @CachePut(key = "'t_'+#id")
    public Optional<Task> updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setName(updatedTask.getName());
                    task.setDescription(updatedTask.getDescription());
                    return taskRepository.save(task);
                });
    }

    @Override
    @CachePut(key = "'t_'+#id")
    public Optional<Task> updateTaskStatus(Long id, String status) {
        String st = status.trim().toUpperCase();
        return taskRepository.findById(id)
                .map(task -> {
                    task.setStatus(st);
                    return taskRepository.save(task);
                });
    }


    @Override
    @CacheEvict(key = "'t_'+#id")
    public Boolean deleteTask(Long id) {
        Optional<Task> taskById = getTaskById(id);
        if (taskById.isEmpty()) {
            return false;
        }
        taskRepository.deleteById(id);
        return true;
    }

    //Everyday Morning at 10 am
    @Scheduled(cron = "10 10 20 * * *")
    public void emailService() {
        List<Task> collect = taskRepository.findAll().stream()
                .filter(task -> task.getStatus().equalsIgnoreCase("PENDING"))
                .collect(Collectors.toList());
        ArrayList<String> emailIds = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < collect.size(); i++) {
            Task task = collect.get(i);

            if (currentDate.isBefore(task.getDueDate()) || currentDate.equals(task.getDueDate())) {

                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(task.getEmail()); // Pass the array of recipients
                message.setSubject("Pending Task - " + task.getName());
                message.setText("Please Complete Your Task. Its Currently Pending and your DueDate - " + task.getDueDate());
                message.setFrom(senderEmail);

                javaMailSender.send(message);

            }
            else {
                task.setStatus("CANCELLED");
                taskRepository.save(task);
            }
        }

        System.out.println("Cron Running Successfully");

    }


}
