package ru.sshell.model;

import ru.sshell.model.enums.TaskStatus;

/**
 * Класс оболочка задача и статус задачи
 */
public class TasktStatusInfo {
    private TaskData taskData;
    private TaskStatus status;

    public TaskData getTaskData() {
        return taskData;
    }

    public TasktStatusInfo setTaskData(TaskData taskData) {
        this.taskData = taskData;
        return this;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TasktStatusInfo setStatus(TaskStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "TaskClientStatusInfo{" +
                "taskData=" + taskData +
                ", status=" + status +
                '}';
    }
}
