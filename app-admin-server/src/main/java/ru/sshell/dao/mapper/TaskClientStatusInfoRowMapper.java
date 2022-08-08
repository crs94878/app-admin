package ru.sshell.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.sshell.model.*;


import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskClientStatusInfoRowMapper implements RowMapper<TaskStatusInfo> {
    @Override
    public TaskStatusInfo mapRow(ResultSet resultSet, int i) throws SQLException {
        return TaskStatusInfo.builder()
                .setStatus(TaskStatus.getTaskStatus(resultSet.getString("status")))
                .setTaskData(mapTaskData(resultSet))
                .build();
    }

    private TaskData mapTaskData(ResultSet resultSet) throws SQLException {
        TaskData taskData =  new TaskData();
        taskData.setId(resultSet.getLong("id"));
        taskData.setName(resultSet.getString("name"));
        taskData.setTaskProcessType(
                TaskProcessType.getTaskProcessType(resultSet.getString("task_type"))
        );
        taskData.setVersion(resultSet.getString("version"));
        taskData.setOs(OS.getOs(resultSet.getString("os")));
        taskData.setOsType(OSType.getOsType(resultSet.getString("os_type")));
        taskData.setPathToRunFile(resultSet.getString("path_to_run_file"));
        taskData.setTorrentFile(resultSet.getString("torrent_file"));
        return taskData;
    }
}
