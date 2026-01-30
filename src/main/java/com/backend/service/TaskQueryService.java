package com.backend.service;

import com.backend.model.Task;
import com.backend.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TaskQueryService {
    private final TaskRepository repository;

    public  TaskQueryService(TaskRepository repository) {
        this.repository = repository;
    }

    public Page<Task> getByStatus(String status,int page,int size) {
        return repository.findByStatus(status,PageRequest.of(page,size));
    }
}
