package ru.sshell.model;

import ru.sshell.model.enums.OS;
import ru.sshell.model.enums.OSType;

/**
 * Класс запрос клиент содержит информацию о клиенте
 */
public class ClientData {
    private Long id;
    private String hostname;
    private OS os;
    private OSType osType;
    private String macAddr;
    private boolean blocked;

    public ClientData() {
        this.blocked = true;
    }

    public Long getId() {
        return id;
    }

    public ClientData setId(Long id) {
        this.id = id;
        return this;
    }

    public String getHostname() {
        return hostname;
    }

    public ClientData setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public OS getOs() {
        return os;
    }

    public ClientData setOs(OS os) {
        this.os = os;
        return this;
    }

    public OSType getOsType() {
        return osType;
    }

    public ClientData setOsType(OSType osType) {
        this.osType = osType;
        return this;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public ClientData setMacAddr(String macAddr) {
        this.macAddr = macAddr;
        return this;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public ClientData setBlocked(boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    @Override
    public String toString() {
        return "ClientData{" +
                "id=" + id +
                ", hostname='" + hostname + '\'' +
                ", os=" + os +
                ", osType=" + osType +
                ", macAddr='" + macAddr + '\'' +
                ", isBlocked=" + blocked +
                '}';
    }
}
