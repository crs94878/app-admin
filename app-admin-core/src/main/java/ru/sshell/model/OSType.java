package ru.sshell.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;

public enum OSType {
    X86("X86"),
    X64("X64");

    private static final Map<String, OSType> OS_TYPE_MAP;

    static {
        OS_TYPE_MAP = new HashMap<>();
        OS_TYPE_MAP.put("x86", X86);
        OS_TYPE_MAP.put("i386", X86);
        OS_TYPE_MAP.put("i486", X86);
        OS_TYPE_MAP.put("i586", X86);
        OS_TYPE_MAP.put("i686", X86);
        OS_TYPE_MAP.put("x86_64", X64);
        OS_TYPE_MAP.put("amd64", X64);
    }

    private final String type;

    OSType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @JsonCreator
    @NonNull
    public static OSType getOsType(String type) {
        return OS_TYPE_MAP
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(type))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Cant find OS type for: " + type)
                );
    }

    @Override
    public String toString() {
        return "OSType{" +
                "type='" + type + '\'' +
                '}';
    }
}
