package com.backend.service;

import com.backend.model.Task;
import com.backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Task create(Task task) {
        return repository.save(task);
    }

    @Transactional
    public Task createAndFail(Task task) {
        Task saved = repository.save(task);
        if (true) {
            throw new RuntimeException("Simulated failure");
        }
        return saved;
    }

    public List<Task> getAll() {
        return repository.findAll();
    }
}
