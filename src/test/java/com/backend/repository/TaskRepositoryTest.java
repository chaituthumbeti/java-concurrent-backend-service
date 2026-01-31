package com.backend.repository;

import com.backend.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TaskRepositoryTest {
    @Autowired
    private TaskRepository repository;

    @Test
    void saveAndFetchTask(){
        Task task=new Task("repo-test","Test");
        Task saved=repository.save(task);

        assertThat(saved.getId()).isNotNull();

        Task fetched=repository.findById(saved.getId()).orElseThrow();
        assertThat(fetched.getTitle()).isEqualTo("repo-test");
        assertThat(fetched.getStatus()).isEqualTo("Test");
    }

}
