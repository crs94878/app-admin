package ru.sshell.model.dto;

import ru.sshell.model.TaskStatus;

/**
 * Транспортный объект для передачи онформации по id клиента, id задачи и статуу этой задачи
 */
public class ClientTaskStatusDto {
    private SimpleClientTaskDataDto clientTaskData;

    private TaskStatus taskStatus;

    public SimpleClientTaskDataDto getClientTaskData() {
        return clientTaskData;
    }

    public void setClientTaskData(SimpleClientTaskDataDto clientTaskData) {
        this.clientTaskData = clientTaskData;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "ClientTaskStatusDto{" +
                "clientTaskData=" + clientTaskData +
                ", taskStatus=" + taskStatus +
                '}';
    }
}
