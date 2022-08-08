package ru.sshell.model;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

/**
 * Задача и статус задачи
 */
@ParametersAreNonnullByDefault
public class TaskStatusInfo {
    @Nonnull
    private final TaskData taskData;

    @Nonnull
    private TaskStatus status;

    private TaskStatusInfo(Builder builder) {
        this.taskData = Objects.requireNonNull(builder.taskData, "taskData");
        this.status = Objects.requireNonNull(builder.status, "status");
    }

    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    public TaskData getTaskData() {
        return taskData;
    }

    @Nonnull
    public TaskStatus getStatus() {
        return status;
    }

    public TaskStatusInfo setStatus(TaskStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "TaskStatusInfo{" +
                "taskData=" + taskData +
                ", status=" + status +
                '}';
    }


    public static class Builder {
        private TaskData taskData;
        private TaskStatus status;

        private Builder() {
        }

        public Builder setTaskData(TaskData taskData) {
            this.taskData = taskData;
            return this;
        }

        public Builder setStatus(TaskStatus status) {
            this.status = status;
            return this;
        }

        public Builder of(TaskStatusInfo taskStatusInfo) {
            this.taskData = taskStatusInfo.taskData;
            this.status = taskStatusInfo.status;
            return this;
        }

        public TaskStatusInfo build() {
            return new TaskStatusInfo(this);
        }
    }
}
