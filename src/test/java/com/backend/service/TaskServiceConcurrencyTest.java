package com.backend.service;

import com.backend.model.Task;
import com.backend.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceConcurrencyTest {

    private TaskRepository repository;
    private ExecutorService executor;
    private TaskService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(TaskRepository.class);
        executor = Executors.newFixedThreadPool(8);
        service = new TaskService(repository, executor);
    }

    @AfterEach
    void tearDown() {
        executor.shutdownNow();
    }
    @Test
    void createAsync_handlesHighConcurrencyCorrectly() throws InterruptedException {
        int requests = 100;

        when(repository.save(any(Task.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        CountDownLatch latch = new CountDownLatch(requests);

        for (int i = 0; i < requests; i++) {
            service.createAsync(new Task("t-" + i, "ASYNC"));
            latch.countDown();
        }

        // wait for tasks to finish
        latch.await(2, TimeUnit.SECONDS);
        Thread.sleep(300);

        assertThat(service.getProcessedCount()).isEqualTo(requests);
        verify(repository, times(requests)).save(any(Task.class));
    }

    @Test
    void failureInOneTaskDoesNotAffectOthers() throws InterruptedException {
        int requests = 20;
        AtomicInteger failures = new AtomicInteger(0);

        when(repository.save(any(Task.class)))
                .thenAnswer(invocation -> {
                    if (failures.incrementAndGet() == 1) {
                        throw new RuntimeException("simulated failure");
                    }
                    return invocation.getArgument(0);
                });

        for (int i = 0; i < requests; i++) {
            try {
                service.createAsync(new Task("t-" + i, "ASYNC"));
            } catch (Exception ignored) {}
        }

        Thread.sleep(500);

        assertThat(service.getProcessedCount()).isEqualTo(requests - 1);
        verify(repository, atLeast(requests - 1)).save(any(Task.class));
    }

}
