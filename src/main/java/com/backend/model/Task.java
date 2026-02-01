package com.backend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "title must not be blank")
    @Column(nullable = false)
    private String title;
    @Pattern(
            regexp="NEW|IN_PROGRESS|DONE",
            message = "status must be one of in NEW,IN_PROGRESS,DONE"
    )
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