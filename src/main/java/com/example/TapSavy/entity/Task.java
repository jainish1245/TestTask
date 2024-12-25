package com.example.TapSavy.entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Table(name = "tasks")
@Entity
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;

    private String name;

    @Column(name = "due_date")
    private LocalDate dueDate;

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    private String description;

    private LocalDate date;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Email cannot be null") @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "Email cannot be null") @Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }








    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
