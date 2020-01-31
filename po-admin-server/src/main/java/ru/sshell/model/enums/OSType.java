package ru.sshell.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OSType {
    X86("X86"),
    X64("X64");

    private final String osType;
    OSType(String osType) {
        this.osType = osType;
    }

    public String getOsType() {
        return osType;
    }

    @JsonCreator
    public static OSType getOsTypeByName(String name) {
        for (OSType osType : OSType.values()) {
            if (osType.osType.equalsIgnoreCase(name)) {
                return osType;
            }
        }
        throw new IllegalArgumentException("Cant find OS type by type: " + name);
    }

    @Override
    public String toString() {
        return "OSType{" +
                "osType='" + osType + '\'' +
                '}';
    }
}
