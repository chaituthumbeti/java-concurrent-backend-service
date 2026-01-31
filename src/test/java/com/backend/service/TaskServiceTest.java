package com.backend.service;

import com.backend.model.Task;
import com.backend.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.DataAccessException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    private ExecutorService executor;

    private TaskService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        executor = Executors.newFixedThreadPool(2);
        service = new TaskService(repository, executor);
    }

    @Test
    void createTask_success() {
        Task input = new Task("test-task", "NEW");
        Task saved = new Task("test-task", "NEW");
        saved.setId(1L);

        when(repository.save(input)).thenReturn(saved);

        Task result = service.create(input);

        assertThat(result.getId()).isEqualTo(1L);
        verify(repository).save(input);
    }

    @Test
    void createTask_repositoryFailure_propagatesException() {
        Task input = new Task("fail-task", "NEW");

        when(repository.save(input))
                .thenThrow(new RuntimeException("DB failure"));

        assertThatThrownBy(() -> service.create(input))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("DB failure");

        verify(repository).save(input);
    }
    @Test
    void createAsync_submitsTask() throws InterruptedException {
        Task input = new Task("async-task", "ASYNC");

        when(repository.save(any(Task.class))).thenReturn(input);

        service.createAsync(input);

        // allow async execution
        Thread.sleep(200);

        verify(repository, times(1)).save(input);
    }

}
