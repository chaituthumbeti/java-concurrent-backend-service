package com.backend.service;

import com.backend.model.Task;
import com.backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
    private final TaskRepository repository;
    private final ExecutorService executor;


    public TaskService(TaskRepository repository,ExecutorService executor) {
        this.repository = repository;
        this.executor = executor;
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

    @Transactional
    public void createAsync(Task task){
        executor.submit(()->create(task));
    }
    public List<Task> getAll() {
        return repository.findAll();
    }
}
