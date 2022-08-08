package ru.sshell.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

/**
 * Информацию о клиенте
 */
@ParametersAreNonnullByDefault
public class ClientData {
    @Nonnull
    private final Long id;
    @Nullable
    private final String hostname;
    @NonNull
    private final OS os;
    @NonNull
    private final OSType osType;
    @Nullable
    private final String macAddr;
    @NonNull
    private Boolean blocked;

    private ClientData(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.hostname = builder.hostname;
        this.os = Objects.requireNonNull(builder.os, "os");
        this.osType = Objects.requireNonNull(builder.osType, "osType");
        this.macAddr = builder.macAddr;
        this.blocked = false;
    }

    public static Builder builder() {
        return new Builder();
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


    @Nonnull
    public Long getId() {
        return id;
    }

    @Nullable
    public String getHostname() {
        return hostname;
    }

    @Nonnull
    public OS getOs() {
        return os;
    }

    @Nonnull
    public OSType getOsType() {
        return osType;
    }

    @Nullable
    public String getMacAddr() {
        return macAddr;
    }

    @JsonIgnore
    public boolean getBlocked() {
        return blocked;
    }

    public static class Builder {
        private Long id;
        private String hostname;
        private OS os;
        private OSType osType;
        private String macAddr;
        private Boolean blocked;

        private Builder() {
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder setOs(OS os) {
            this.os = os;
            return this;
        }

        public Builder setOsType(OSType osType) {
            this.osType = osType;
            return this;
        }

        public Builder setMacAddr(@Nullable String macAddr) {
            this.macAddr = macAddr;
            return this;
        }

        public Builder setBlocked(boolean blocked) {
            this.blocked = blocked;
            return this;
        }

        public Builder of(ClientData clientData) {
            this.id = clientData.id;
            this.hostname = clientData.hostname;
            this.os = clientData.os;
            this.osType = clientData.osType;
            this.macAddr = clientData.macAddr;
            this.blocked = clientData.blocked;
            return this;
        }

        public ClientData build() {
            return new ClientData(this);
        }
    }
}
