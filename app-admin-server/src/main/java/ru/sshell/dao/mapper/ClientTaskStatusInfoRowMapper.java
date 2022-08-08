package ru.sshell.dao.mapper;


import org.springframework.jdbc.core.RowMapper;
import ru.sshell.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientTaskStatusInfoRowMapper implements RowMapper<ClientTaskStatusInfo> {

    @Override
    public ClientTaskStatusInfo mapRow(ResultSet resultSet, int i) throws SQLException {
        return new ClientTaskStatusInfo()
                .setTaskStatus(TaskStatus.getTaskStatus(resultSet.getString("status")))
                .setClientData(mapClientData(resultSet));
    }

    private ClientData mapClientData(ResultSet resultSet) throws SQLException {
        return ClientData.builder()
                .setId(resultSet.getLong("id"))
                .setHostname(resultSet.getString("host_name"))
                .setOs(OS.getOs(resultSet.getString("os")))
                .setOsType(OSType.getOsType(resultSet.getString("os_type")))
                .setMacAddr(resultSet.getString("mac_addr"))
                .build();
    }
}
