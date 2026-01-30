package com.backend.concurrency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

@Configuration
public class TaskExecutorConfig {

    @Bean
    public ExecutorService taskExecutor(){
        return Executors.newFixedThreadPool(5);
    }

}
