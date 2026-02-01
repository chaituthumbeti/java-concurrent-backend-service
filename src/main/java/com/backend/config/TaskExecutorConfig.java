package com.backend.config;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
public class TaskExecutorConfig {

    private final ExecutorService executor;

    public TaskExecutorConfig(@Value("${executor.pool.size}") int poolSize) {
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    @Bean
    public ExecutorService taskExecutor() {
        return executor;
    }

    @PreDestroy
    public void shutdownExecutor() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
