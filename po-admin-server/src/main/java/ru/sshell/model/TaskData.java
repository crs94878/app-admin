package ru.sshell.model;

import ru.sshell.model.enums.OS;
import ru.sshell.model.enums.OSType;
import ru.sshell.model.enums.TaskProcessType;

import java.util.Objects;

public class TaskData {
    private Long id;
    private String name;
    private TaskProcessType taskProcessType;
    private String version;
    private OS os;
    private OSType osType;
    private String pathToRunFile;
    private String torrentFile;

    public TaskData(Long id, String name, TaskProcessType taskProcessType, String version, OS os, OSType osType, String pathToRunFile, String torrentFile) {
        this.id = id;
        this.name = name;
        this.taskProcessType = taskProcessType;
        this.version = version;
        this.os = os;
        this.osType = osType;
        this.pathToRunFile = pathToRunFile;
        this.torrentFile = torrentFile;
    }

    public TaskData() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskProcessType getTaskProcessType() {
        return taskProcessType;
    }

    public void setTaskProcessType(TaskProcessType taskProcessType) {
        this.taskProcessType = taskProcessType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public OS getOs() {
        return os;
    }

    public void setOs(OS os) {
        this.os = os;
    }

    public OSType getOsType() {
        return osType;
    }

    public void setOsType(OSType osType) {
        this.osType = osType;
    }

    public String getPathToRunFile() {
        return pathToRunFile;
    }

    public void setPathToRunFile(String pathToRunFile) {
        this.pathToRunFile = pathToRunFile;
    }

    public String getTorrentFile() {
        return torrentFile;
    }

    public void setTorrentFile(String torrentFile) {
        this.torrentFile = torrentFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskData)) return false;
        TaskData that = (TaskData) o;
        return id.equals(that.id) &&
                getName().equals(that.getName()) &&
                getTaskProcessType() == that.getTaskProcessType() &&
                getVersion().equals(that.getVersion()) &&
                getOs().getOs() == that.getOs().getOs() &&
                getOsType().getOsType() == that.getOsType().getOsType() &&
                getPathToRunFile().equals(that.getPathToRunFile()) &&
                getTorrentFile().equals(that.getTorrentFile());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getName(), getTaskProcessType(), getVersion(), getOs().getOs(), getOsType().getOsType(), getPathToRunFile(), getTorrentFile());
    }

    @Override
    public String toString() {
        return "TaskDataDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", taskProcessType=" + taskProcessType +
                ", version='" + version + '\'' +
                ", os=" + os.getOs() +
                ", osType=" + osType.getOsType() +
                ", pathToRunFile='" + pathToRunFile + '\'' +
                ", torrentFile=" + torrentFile +
                '}';
    }
}
