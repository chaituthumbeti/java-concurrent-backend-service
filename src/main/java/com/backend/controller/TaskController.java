package com.backend.controller;

import com.backend.model.Task;
import com.backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public Task create(@Valid @RequestBody Task task) {
        return service.create(task);
    }

    @PostMapping("/fail")
    public Task createWithFailure(@RequestBody Task task) {
        return service.createAndFail(task);
    }

    @GetMapping
    public List<Task> getAll() {
        return service.getAll();
    }

    @PostMapping("/async")
    public String createAsync(@RequestBody Task task){
        service.createAsync(task);
        return "Accepted";
    }
    @GetMapping("/processed-count")
    public int getProcessedCount() {
        return service.getProcessedCount();
    }
}
