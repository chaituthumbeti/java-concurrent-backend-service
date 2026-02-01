package com.backend;

import com.backend.model.Task;
import com.backend.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class IntegrationFlowTest {
    @Autowired
    private TaskRepository repository;

    @Test
    @Transactional
    void createTask_persistsSuccessfully(){
        Task task=new Task("integration-task","NEW");
        repository.save(task);

        assertThat(repository.findAll())
                .extracting(Task::getTitle)
                .contains("integration-task");
    }

    @Test
    void transactionRollback_onFailure(){
        try {
            Task task=new Task("rollback-task","NEW");
            repository.save(task);
            throw new RuntimeException("faiure");
        }catch(Exception ignored){}
        assertThat(repository.findAll())
                .extracting(Task::getTitle)
                .doesNotContain("rollback-task");
    }
}
