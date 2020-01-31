package ru.sshell.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskProcessType {
    BAT("BAT"),
    POWERSHELL("POWERSHELL"),
    PROGRAM("PROGRAM"),
    SH("SH");

    private final String type;
    TaskProcessType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static TaskProcessType getTaskProcessTypeByName(String name) {
        for (TaskProcessType processType : TaskProcessType.values()) {
            if (processType.type.equalsIgnoreCase(name)) {
                return processType;
            }
        }
        throw new IllegalArgumentException("Cant find task process type by name: " + name);
    }

    @Override
    public String toString() {
        return "TaskType{" +
                "type='" + type + '\'' +
                '}';
    }
}
