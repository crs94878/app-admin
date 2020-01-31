package ru.sshell.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OS {
    WINDOWS("WINDOWS"),
    LINUX("LINUX"),
    MACOS("MACOS");

    private final String os;

    OS(String os) {
        this.os = os;
    }

    @JsonValue
    public String getOs() {
        return os;
    }

    @JsonCreator
    public static OS getOSByName (String name) {
        for (OS os : OS.values()) {
            if (os.os.equalsIgnoreCase(name)) {
                return os;
            }
        }
        throw new IllegalArgumentException("Cant find OS, by type: " + name);
    }

    @Override
    public String toString() {
        return "OS{" +
                "os='" + os + '\'' +
                '}';
    }
}
