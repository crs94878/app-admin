package ru.sshell.model;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;


@ParametersAreNonnullByDefault
public class TaskData {

    @Nonnull
    private final Long id;
    @Nonnull
    private final String name;
    @Nonnull
    private final TaskProcessType taskProcessType;
    @Nonnull
    private final String version;
    @Nonnull
    private final OS os;
    @Nonnull
    private final OSType osType;
    @Nonnull
    private final String pathToRunFile;
    @Nonnull
    private final String torrentFile;

    private TaskData(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.taskProcessType = Objects.requireNonNull(builder.taskProcessType, "taskProcessType");
        this.version = Objects.requireNonNull(builder.version, "version");
        this.os = Objects.requireNonNull(builder.os, "os");
        this.osType = Objects.requireNonNull(builder.osType, "osType");
        this.pathToRunFile = Objects.requireNonNull(builder.pathToRunFile, "pathToRunFile");
        this.torrentFile = Objects.requireNonNull(builder.torrentFile, "torrentFile");
    }

    @Nonnull
    public Long getId() {
        return id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public TaskProcessType getTaskProcessType() {
        return taskProcessType;
    }

    @Nonnull
    public String getVersion() {
        return version;
    }

    @Nonnull
    public OS getOs() {
        return os;
    }

    @Nonnull
    public OSType getOsType() {
        return osType;
    }

    @Nonnull
    public String getPathToRunFile() {
        return pathToRunFile;
    }

    @Nonnull
    public String getTorrentFile() {
        return torrentFile;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "TaskDataDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", taskProcessType=" + taskProcessType +
                ", version='" + version + '\'' +
                ", os=" + os.getName() +
                ", osType=" + osType.getType() +
                ", pathToRunFile='" + pathToRunFile + '\'' +
                ", torrentFile=" + torrentFile +
                '}';
    }

    public static class Builder {
        private Long id;
        private String name;
        private TaskProcessType taskProcessType;
        private String version;
        private OS os;
        private OSType osType;
        private String pathToRunFile;
        private String torrentFile;

        private Builder() {
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTaskProcessType(TaskProcessType taskProcessType) {
            this.taskProcessType = taskProcessType;
            return this;
        }

        public Builder setVersion(String version) {
            this.version = version;
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

        public Builder setPathToRunFile(String pathToRunFile) {
            this.pathToRunFile = pathToRunFile;
            return this;
        }

        public Builder setTorrentFile(String torrentFile) {
            this.torrentFile = torrentFile;
            return this;
        }

        public Builder of(TaskData taskData) {
            this.id = taskData.id;
            this.name = taskData.name;
            this.taskProcessType = taskData.taskProcessType;
            this.version = taskData.version;
            this.os = taskData.os;
            this.osType = taskData.osType;
            this.pathToRunFile = taskData.pathToRunFile;
            this.torrentFile = taskData.torrentFile;
            return this;
        }

        public TaskData build() {
            return new TaskData(this);
        }
    }
}
