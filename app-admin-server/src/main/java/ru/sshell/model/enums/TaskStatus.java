package ru.sshell.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {
    NEW("NEW"),
    PREPARING("PREPARING"),
    IN_QUEUE("IN QUEUE"),
    DOWNLOADING("DOWNLOADING"),
    ERROR_INSTALLING( "ERROR INSTALLING"),
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
    public static TaskStatus getTaskStatusByName(String name) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.status.equalsIgnoreCase(name)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Cant find status by name: " + name);
    }
    @Override
    public String toString() {
        return "TaskStatus{" +
                "status='" + status + '\'' +
                '}';
    }
}
