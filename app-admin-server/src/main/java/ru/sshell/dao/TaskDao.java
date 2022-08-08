package ru.sshell.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.sshell.model.ClientTaskStatusInfo;
import ru.sshell.model.TaskData;
import ru.sshell.model.TaskStatus;
import ru.sshell.model.TaskStatusInfo;
import ru.sshell.model.dto.ClientTaskStatusDto;
import ru.sshell.model.dto.SimpleClientTaskDataDto;

import java.util.List;

public class TaskDao extends AbstractDao {

    @Autowired
    public TaskDao(NamedParameterJdbcTemplate parameterJdbcTemplate) {
        super(parameterJdbcTemplate);
    }


    public void addTask(TaskData task) {
        MapSqlParameterSource mapSource =  new MapSqlParameterSource()
                .addValue("name", task.getName())
                .addValue("taskType", task.getTaskProcessType().getType())
                .addValue("version", task.getVersion())
                .addValue("os", task.getOs().getName())
                .addValue("osType", task.getOsType().getType())
                .addValue("pathToRunFile", task.getPathToRunFile())
                .addValue("torrentFile", task.getTorrentFile());
        parameterJdbcTemplate.update(TaskDaoQueries.ADD_TASK_QUERY, mapSource);
    }

    public TaskData getTask(Long id) {
        MapSqlParameterSource mapSource =  new MapSqlParameterSource()
                .addValue("id", id);
        return parameterJdbcTemplate.query(TaskDaoQueries.GET_TASK_QUERY, mapSource, taskDataListRowMapper).get(0);
    }


    public List<TaskData> getAllTasks() {
        return parameterJdbcTemplate.query(TaskDaoQueries.GET_ALL_TASKS, taskDataListRowMapper);
    }

    public int deleteById(Long id) {
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("id", id);
        return parameterJdbcTemplate.update(TaskDaoQueries.DELETE_TASK_BY_ID, mapSource);
    }

    public int updateTask(TaskData task) {
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("id", task.getId())
                .addValue("name", task.getName())
                .addValue("taskType", task.getTaskProcessType().name())
                .addValue("version", task.getVersion())
                .addValue("os", task.getOs().name())
                .addValue("osType", task.getOsType().name())
                .addValue("pathToRunFile", task.getPathToRunFile())
                .addValue("torrentFile", task.getTorrentFile());
        return parameterJdbcTemplate.update(TaskDaoQueries.UPDATE_TASK, mapSource);
    }

    public List<TaskStatusInfo> getTasksForClient(Long id) {
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("id", id);
        return parameterJdbcTemplate.query(TaskDaoQueries.GET_CLIENT_TASKS_FOR_EDIT, mapSource, taskDataDtoRowMapper);
    }

    public List<Long> getActiveTasksByClientID(Long clientID) {
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("id", clientID);
        return parameterJdbcTemplate.query(TaskDaoQueries.GET_ACTIVE_CLIENT_TASK_FOR_RUN, mapSource, longRowMapper);
    }

    public int addTaskForClient(Long idClient, Long idTask) {
        MapSqlParameterSource mapSource =  new MapSqlParameterSource()
                .addValue("clientId", idClient)
                .addValue("taskId", idTask)
                .addValue("status", TaskStatus.PREPARING.getStatus());
        return parameterJdbcTemplate.update(TaskDaoQueries.ADD_TASK_TO_CLIENT_BY_ID, mapSource);
    }

    public int deleteTaskForClient(SimpleClientTaskDataDto simpleClientTaskDataDto) {
        MapSqlParameterSource mapSource =  new MapSqlParameterSource()
                .addValue("clientId", simpleClientTaskDataDto.getClientId())
                .addValue("taskId", simpleClientTaskDataDto.getTaskId());
        return parameterJdbcTemplate.update(TaskDaoQueries.DELETE_TASK_TO_CLIENT_BY_ID, mapSource);
    }

    public List<ClientTaskStatusInfo> getClientsForTask(Long id) {
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("id", id);
        return parameterJdbcTemplate.query(TaskDaoQueries.GET_CLIENTS_FOR_TASK, mapSource, clientDataListRowMapper);
    }

    public List<TaskData> getTaskById(Long taskId) {
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("taskId", taskId);
        return parameterJdbcTemplate.query(TaskDaoQueries.GET_TASK_BY_ID, mapSource, taskDataListRowMapper);
    }

    public void updateStatus(ClientTaskStatusDto clientTaskStatusDto) {
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("taskId", clientTaskStatusDto.getClientTaskData().getTaskId())
                .addValue("clientId", clientTaskStatusDto.getClientTaskData().getClientId())
                .addValue("status", clientTaskStatusDto.getTaskStatus().getStatus());
        parameterJdbcTemplate.update(TaskDaoQueries.UPDATE_TASK_STATUS, mapSource);
    }

    /**
     * Вложенный статический класс с запросами в БД
     */
    private static final class TaskDaoQueries {

        private static final String ADD_TASK_QUERY = "INSERT INTO tasks(name, taskType, version, os, osType, pathToRunFile, torrentFile)"+
                " VALUES (:name, :taskType, :version, :os, :osType, :pathToRunFile, :torrentFile)";
        private static final String GET_TASK_QUERY = "SELECT * FROM tasks WHERE id = :id";
        private static final String GET_ALL_TASKS = "SELECT * FROM tasks";
        private static final String DELETE_TASK_BY_ID = "DELETE FROM tasks WHERE id = :id";
        private static final String UPDATE_TASK = "UPDATE tasks SET name = :name, taskType = :taskType, " +
                "version = :version, os = :os, osType = :osType, pathToRunFile = :pathToRunFile, torrentFile = :torrentFile WHERE id = :id";
        private static final String GET_CLIENT_TASKS_FOR_EDIT = "SELECT t.* , c.status FROM tasks t " +
                "JOIN clienttasks c " +
                "ON  c.taskId = t.id " +
                "WHERE c.clientId = :id";
        private static final String GET_CLIENTS_FOR_TASK = "SELECT c.* , t.status FROM clients c " +
                "JOIN clienttasks t " +
                "ON  t.clientId = c.id " +
                "WHERE t.taskId = :id";
        private static final String ADD_TASK_TO_CLIENT_BY_ID = "INSERT INTO clienttasks(taskId, clientId, status) " +
                "VALUES (:taskId, :clientId, :status)";
        private static final String DELETE_TASK_TO_CLIENT_BY_ID = "DELETE FROM clienttasks WHERE taskId = :taskId AND clientId = :clientId";
        private static final String UPDATE_TASK_STATUS = "UPDATE clienttasks SET status = :status WHERE taskId = :taskId AND clientId = :clientId";

        private static final String GET_ACTIVE_CLIENT_TASK_FOR_RUN = "SELECT t.id  FROM tasks t " +
                "JOIN clienttasks c " +
                "ON  c.taskId = t.id " +
                "WHERE c.clientId = :id AND c.status = 'PREPARING'";

        private static final String GET_TASK_BY_ID = "SELECT t.*  FROM tasks t " +
                "JOIN clienttasks c ON  c.taskId = t.id " +
                "JOIN clients b ON b.id = clientId " +
                "WHERE c.taskId = :taskId AND c.status = 'PREPARING'";
    }
}
