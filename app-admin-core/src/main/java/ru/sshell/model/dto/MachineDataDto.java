package ru.sshell.model.dto;

import org.springframework.lang.NonNull;
import ru.sshell.model.OS;
import ru.sshell.model.OSType;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author sshell
 * Date: 24.03.2020
 */
@ParametersAreNonnullByDefault
public class MachineDataDto {

    private Long machineId;
    private String hostname;
    private OS os;
    private OSType osType;
    private String macAddr;
    private Boolean blocked;

    public MachineDataDto setMachineId(Long machineId) {
        this.machineId = machineId;
        return this;
    }

    @Nullable
    public Long getMachineId() {
        return machineId;
    }

    @NonNull
    public String getHostname() {
        return hostname;
    }

    public MachineDataDto setHostname(@NonNull String hostname) {
        this.hostname = hostname;
        return this;
    }

    @NonNull
    public OS getOs() {
        return os;
    }

    public MachineDataDto setOs(OS os) {
        this.os = os;
        return this;
    }

    @NonNull
    public OSType getOsType() {
        return osType;
    }

    public MachineDataDto setOsType(OSType osType) {
        this.osType = osType;
        return this;
    }

    @Nullable
    public String getMacAddr() {
        return macAddr;
    }

    public MachineDataDto setMacAddr(@Nullable String macAddr) {
        this.macAddr = macAddr;
        return this;
    }

    @NonNull
    public Boolean getBlocked() {
        return blocked;
    }

    public MachineDataDto setBlocked(Boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    @Override
    public String toString() {
        return "ClientDataDto{" +
                "id=" + machineId +
                ", hostname='" + hostname + '\'' +
                ", os=" + os +
                ", osType=" + osType +
                ", macAddr='" + macAddr + '\'' +
                ", blocked=" + blocked +
                '}';
    }
}
