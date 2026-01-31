package com.backend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String title;
    private String status;
    public Task (){}
    public Task(String title, String status) {
        this.title = title;
        this.status = status;
    }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getStatus() { return status; }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id=id;
    }
}