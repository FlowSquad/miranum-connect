package io.miragon.miranum.platform.tasklist.application.port.out.engine;

import io.miragon.miranum.platform.tasklist.domain.Task;

import java.util.List;
import java.util.Map;


public interface TaskOutPort {

    List<Task> getTasksForUser(String user);


    List<Task> getTasksForUserGroup(String user, String group);


    Task getTask(String user, String taskId);

    Map<String, Object> getTaskData(String user, String taskId);
}