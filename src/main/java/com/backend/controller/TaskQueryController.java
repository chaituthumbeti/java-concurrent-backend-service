package com.backend.controller;

import com.backend.model.Task;
import com.backend.repository.TaskRepository;
import com.backend.service.TaskQueryService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks/query")
public class TaskQueryController {
    private final TaskQueryService service;
    public TaskQueryController(TaskQueryService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Task> getByStatus(
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.getByStatus(status,page,size);
    }
}
