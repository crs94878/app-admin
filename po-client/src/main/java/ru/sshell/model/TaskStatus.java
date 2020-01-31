package ru.sshell.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Статусы задачи
 */
public enum TaskStatus {
    PREPARING("PREPARING"),
    IN_QUEUE("IN QUEUE"),
    DOWNLOADING("DOWNLOADING"),
    ERROR_INSTALLING("ERROR INSTALLING"),
    INSTALLING("INSTALLING");

    private final String status;

    TaskStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }

    @JsonCreator
    public static TaskStatus getByName(String name) {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.status.equalsIgnoreCase(name)) {
                return taskStatus;
            }
        }
        throw new IllegalArgumentException("Cant find status by name: " + name);
    }

    @Override
    public String toString() {
        return "TaskStatus{" +
                ", status='" + status + '\'' +
                '}';
    }
}
