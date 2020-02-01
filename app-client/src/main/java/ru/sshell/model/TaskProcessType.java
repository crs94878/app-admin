package ru.sshell.model;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Типы запускаемых файлов для запуска выполнения задачи
 */
public enum TaskProcessType {
    BAT("BAT"),
    POWERSHELL("POWERSHELL"),
    PROGRAM("PROGRAM"),
    SH("SH");

    private String type;
    TaskProcessType(String type) {
        this.type = type;
    }

    public String getValue() {
        return type;
    }

    @JsonCreator
    public static TaskProcessType getProcessTypeByName(String name) {
        for (TaskProcessType taskProcessType : TaskProcessType.values()) {
            if (taskProcessType.type.equalsIgnoreCase(name)) {
                return taskProcessType;
            }
        }
        throw new IllegalArgumentException("Error while gat task process type by name: " + name);
    }

    @Override
    public String toString() {
        return "TaskType{" +
                "type='" + type + '\'' +
                '}';
    }
}
