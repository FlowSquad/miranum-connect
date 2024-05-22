package io.miragon.miranum.platform.tasklist;

import io.miragon.miranum.platform.tasklist.adapter.out.engine.TaskCommandEngineAdapter;
import io.miragon.miranum.platform.tasklist.adapter.out.task.TaskMapper;
import io.miragon.miranum.platform.tasklist.adapter.out.task.TaskPersistenceAdapter;
import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskInfoRepository;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskCommandPort;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import org.camunda.bpm.engine.TaskService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"io.miragon.miranum.platform.tasklist"})
@EnableJpaRepositories(basePackages = {"io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo"})
@EntityScan(basePackages = {"io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo"})
public class TasklistConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TaskOutPort taskOutPort(final TaskService taskService, final TaskInfoRepository taskInfoRepository, final TaskMapper taskMapper) {
        return new TaskPersistenceAdapter(taskService, taskInfoRepository, taskMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public TaskCommandPort taskCommandPort(final TaskService taskService, final TaskInfoRepository taskInfoRepository) {
        return new TaskCommandEngineAdapter(taskService, taskInfoRepository);
    }
}
