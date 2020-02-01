package ru.sshell.dao.mapper;


import org.springframework.jdbc.core.RowMapper;
import ru.sshell.model.ClientData;
import ru.sshell.model.ClientTaskStatusInfo;
import ru.sshell.model.enums.OS;
import ru.sshell.model.enums.OSType;
import ru.sshell.model.enums.TaskStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientTaskStatusInfoRowMapper implements RowMapper<ClientTaskStatusInfo> {

    @Override
    public ClientTaskStatusInfo mapRow(ResultSet resultSet, int i) throws SQLException {
        return new ClientTaskStatusInfo()
                .setTaskStatus(TaskStatus.getTaskStatusByName(resultSet.getString("status")))
                .setClientData(mapClientData(resultSet));
    }

    private ClientData mapClientData(ResultSet resultSet) throws SQLException {
        return new ClientData()
                .setId(resultSet.getLong("id"))
                .setHostname(resultSet.getString("hostName"))
                .setOs(OS.getOSByName(resultSet.getString("os")))
                .setOsType(OSType.getOsTypeByName(resultSet.getString("osType")))
                .setMacAddr(resultSet.getString("macAddr"));
    }
}
