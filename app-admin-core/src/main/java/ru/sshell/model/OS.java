package ru.sshell.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Тип операционной системы
 * @author sshell
 */
public enum OS {
    WINDOWS("WINDOWS"),
    LINUX("LINUX"),
    MACOS("MACOS");

    private static final Map<String, OS> OS_MAP;

    static {
        OS_MAP = Arrays.stream(OS.values()).collect(Collectors.toMap(os -> os.name, os -> os));
    }

    private final String name;

    OS(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    @NonNull
    public static OS getOs(String name) {
        return OS_MAP
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(name))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Cannot find OS for: " + name)
                );
    }

    @Override
    public String toString() {
        return "OS{" +
                ", name='" + name + '\'' +
                '}';
    }
}
