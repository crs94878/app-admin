package ru.sshell.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.sshell.model.TasktStatusInfo;
import ru.sshell.model.TaskData;
import ru.sshell.model.enums.OS;
import ru.sshell.model.enums.OSType;
import ru.sshell.model.enums.TaskStatus;
import ru.sshell.model.enums.TaskProcessType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskClientStatusInfoRowMapper implements RowMapper<TasktStatusInfo> {
    @Override
    public TasktStatusInfo mapRow(ResultSet resultSet, int i) throws SQLException {
        return new TasktStatusInfo()
                .setStatus(TaskStatus.getTaskStatusByName(resultSet.getString("status")))
                .setTaskData(mapTaskData(resultSet));
    }

    private TaskData mapTaskData(ResultSet resultSet) throws SQLException {
        TaskData taskData =  new TaskData();
        taskData.setId(resultSet.getLong("id"));
        taskData.setName(resultSet.getString("name"));
        taskData.setTaskProcessType(TaskProcessType
                .getTaskProcessTypeByName(resultSet.getString("taskType")));
        taskData.setVersion(resultSet.getString("version"));
        taskData.setOs(OS.getOSByName(resultSet.getString("os")));
        taskData.setOsType(OSType.getOsTypeByName(resultSet.getString("osType")));
        taskData.setPathToRunFile(resultSet.getString("pathToRunFile"));
        taskData.setTorrentFile(resultSet.getString("torrentFile"));
        return taskData;
    }
}
