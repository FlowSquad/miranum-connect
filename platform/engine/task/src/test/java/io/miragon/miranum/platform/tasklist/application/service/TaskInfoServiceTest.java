package io.miragon.miranum.platform.tasklist.application.service;

import io.miragon.miranum.platform.engine.application.port.out.process.MiranumProcessDefinitionPort;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import io.miragon.miranum.platform.tasklist.application.port.in.TaskInfoUseCase;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.TaskInfo;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TaskInfoServiceTest {

    private final TaskOutPort taskOutPort = mock(TaskOutPort.class);
    private final MiranumProcessDefinitionPort miranumProcessDefinitionPort = mock(MiranumProcessDefinitionPort.class);

    private final TaskInfoUseCase taskInfoService = new TaskInfoService(taskOutPort, miranumProcessDefinitionPort);

    @Test
    void testCreateTaskInfo() {
        // Arrange
        final DelegateTask mockTask = mock(DelegateTask.class);
        when(mockTask.getId()).thenReturn("task123");
        when(mockTask.getProcessDefinitionId()).thenReturn("processDef123");
        when(mockTask.getProcessInstanceId()).thenReturn("instance123");
        when(mockTask.getAssignee()).thenReturn("user123");
        when(mockTask.getCandidates()).thenReturn(Set.of());

        MiranumProcessDefinition mockDefinition = MiranumProcessDefinition.builder()
                .key("processDef123")
                .name("Process Name")
                .build();
        when(miranumProcessDefinitionPort.getProcessDefinitionById("processDef123")).thenReturn(mockDefinition);

        // Act
        taskInfoService.createTaskInfo(mockTask);

        // Assert
        final ArgumentCaptor<TaskInfo> taskInfoCaptor = ArgumentCaptor.forClass(TaskInfo.class);
        verify(taskOutPort).createTaskInfo(taskInfoCaptor.capture());
        assertThat(taskInfoCaptor.getValue().getId()).isEqualTo("task123");
        assertThat(taskInfoCaptor.getValue().getDescription()).isBlank();
        assertThat(taskInfoCaptor.getValue().getDefinitionName()).isEqualTo("Process Name");
        assertThat(taskInfoCaptor.getValue().getInstanceId()).isEqualTo("instance123");
        assertThat(taskInfoCaptor.getValue().getAssignee()).isEqualTo("user123");
        assertThat(taskInfoCaptor.getValue().getCandidateUsers()).isNull();
        assertThat(taskInfoCaptor.getValue().getCandidateGroups()).isNull();
        assertThat(taskInfoCaptor.getValue().getForm()).isBlank();
    }

    @Test
    void testDeleteTaskInfo() {
        final String taskId = "12345";
        taskInfoService.deleteTaskInfo(taskId);
        verify(taskOutPort).deleteTaskInfo(taskId);
    }

}
