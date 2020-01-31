package ru.sshell.model.dto;

import java.util.List;

/**
 * Объект оболочка для отправки списка задач наружу
 */
public class TaskPackDto {

    private List<Long> taskDatas;

    public TaskPackDto() {
    }

    public TaskPackDto(List<Long> taskDatas) {
        this.taskDatas = taskDatas;
    }

    public List<Long> getTaskDatas() {
        return taskDatas;
    }

    public void setTaskDatas(List<Long> taskDatas) {
        this.taskDatas = taskDatas;
    }

    @Override
    public String toString() {
        return "TaskPackDto{" +
                "taskDatas=" + taskDatas +
                '}';
    }
}
