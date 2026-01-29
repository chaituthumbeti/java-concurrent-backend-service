package com.backend.service;

import com.backend.model.Task;
import com.backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService{
    private final TaskRepository repository;
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }
    public Task create(Task task){
        return repository.save(task);
    }
    public List<Task> getAll(){
        return repository.findAll();
    }
}
