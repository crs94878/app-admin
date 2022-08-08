package ru.sshell.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TaskProcessType {
    BAT("BAT"),
    POWERSHELL("POWERSHELL"),
    PROGRAM("PROGRAM"),
    SH("SH");

    private static final Map<String, TaskProcessType> TASK_PROCESS_TYPE_MAP;

    static {
        TASK_PROCESS_TYPE_MAP = Arrays.stream(TaskProcessType.values())
                .collect(Collectors.toMap(taskProcessType -> taskProcessType.type,
                        taskProcessType -> taskProcessType));
    }

    private final String type;

    TaskProcessType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    @NonNull
    public static TaskProcessType getTaskProcessType(String type) {
        return TASK_PROCESS_TYPE_MAP
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(type))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Cant find task process type for: " + type)
                );
    }

    @Override
    public String toString() {
        return "TaskProcessType{" +
                ", type='" + type + '\'' +
                '}';
    }
}
