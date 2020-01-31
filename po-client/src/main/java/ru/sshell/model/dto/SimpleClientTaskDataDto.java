package ru.sshell.model.dto;

/**
 * Объект для передачи информации по id клинта и id задачи
 */
public class SimpleClientTaskDataDto {
    private Long clientId;
    private Long taskId;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "ClientTaskActionDto{" +
                "clientId=" + clientId +
                ", taskId=" + taskId +
                '}';
    }
}
