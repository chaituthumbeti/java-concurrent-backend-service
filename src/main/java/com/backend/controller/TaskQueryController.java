package com.backend.controller;

import com.backend.model.Task;
import com.backend.repository.TaskRepository;
import com.backend.service.TaskQueryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

@Validated
@RestController
@RequestMapping("/tasks/query")
public class TaskQueryController {
    private final TaskQueryService service;
    public TaskQueryController(TaskQueryService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Task> getByStatus(
            @RequestParam
            @NotBlank(message = "status must nit be blank")
            @Pattern(
                    regexp = "NEW|IN_PROGRESS|DONE",
                    message = "status must be one of in NEW,IN_PROGRESS,DONE"
            )
            String status,

            @RequestParam(defaultValue = "0")
            @Min(value=0,message = "size must be>=0")
            int page,

            @RequestParam(defaultValue = "10")
            @Min(value=1,message = "size must be>=1")
            @Max(value=100,message = "size must be <=100")
            int size) {
        return service.getByStatus(status,page,size);
    }
}
