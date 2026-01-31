package com.backend.controller;

import com.backend.model.Task;
import com.backend.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTask_returnsCreatedTask() throws Exception {
        Task saved = new Task("test-task", "NEW");
        saved.setId(1L);

        when(service.create(any(Task.class))).thenReturn(saved);

        String body = """
        {
          "title": "test-task",
          "status": "NEW"
        }
        """;

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("test-task"))
                .andExpect(jsonPath("$.status").value("NEW"));
    }

    @Test
    void getTasks_returnsList() throws Exception {
        when(service.getAll())
                .thenReturn(java.util.List.of(
                        new Task("t1", "NEW"),
                        new Task("t2", "DONE")
                ));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("t1"))
                .andExpect(jsonPath("$[1].status").value("DONE"));
    }
    @Test
    void createTask_invalidPayload_returnsBadRequest() throws Exception {
        String body = """
        {
          "status": "NEW"
        }
        """;

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
    @Test
    void createTask_serviceFailure_returnsServerError() throws Exception {
        when(service.create(any(Task.class)))
                .thenThrow(new RuntimeException("failure"));

        String body = """
        {
          "title": "x",
          "status": "NEW"
        }
        """;

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isInternalServerError());
    }

}
