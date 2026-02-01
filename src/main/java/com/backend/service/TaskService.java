package com.backend.service;

import com.backend.model.Task;
import com.backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TaskService {
    private final TaskRepository repository;
    private final ExecutorService executor;
    private final AtomicInteger processedCount = new AtomicInteger(0);
    private static final Logger log=LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository repository,ExecutorService executor) {
        this.repository = repository;
        this.executor = executor;
    }

    @Transactional
    public Task create(Task task) {
        log.info("Creating task synchronously: title={}", task.getTitle());
        return repository.save(task);
    }

    @Transactional
    public Task createAndFail(Task task) {
        log.info("Creating task with forced failure : title={}", task.getTitle());
        Task saved = repository.save(task);
        if (true) {
            throw new RuntimeException("Simulated failure");
        }
        return saved;
    }

    public void createAsync(Task task){
        log.info(
                "Submitting async task creation: title={}, CallerThread={}",
                task.getTitle(),
                Thread.currentThread().getName()
        );
        executor.submit(() -> transactionalAsyncCreate(task));
    }
    @Transactional
    public void transactionalAsyncCreate(Task task) {
        log.info(
                "Executing async transactional save: title={},WorkerThread={}",
                task.getTitle(),
                Thread.currentThread().getName()
        );
        repository.save(task);
        processedCount.incrementAndGet();
    }
    public int getProcessedCount(){
        return processedCount.get();
    }
    public List<Task> getAll() {
        return repository.findAll();
    }
}
