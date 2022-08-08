package ru.sshell.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Статусы клиентских задач
 * @author sshell
 */
public enum TaskStatus {
    NEW("NEW"),
    PREPARING("PREPARING"),
    IN_QUEUE("IN_QUEUE"),
    DOWNLOADING("DOWNLOADING"),
    ERROR_INSTALLING("ERROR_INSTALLING"),
    INSTALLING("INSTALLING");

    private static final Map<String, TaskStatus> TASK_STATUS_MAP;
    static {
        TASK_STATUS_MAP = Arrays.stream(TaskStatus.values())
                .collect(Collectors.toMap(taskStatus -> taskStatus.status, taskStatus -> taskStatus));
    }

    private final String status;

    TaskStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }

    @JsonCreator
    @NonNull
    public static TaskStatus getTaskStatus(String status) {
        return TASK_STATUS_MAP
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(status))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Cant find status for: " + status)
                );
    }

    @Override
    public String toString() {
        return "TaskStatus{" +
                ", status='" + status + '\'' +
                '}';
    }
}
