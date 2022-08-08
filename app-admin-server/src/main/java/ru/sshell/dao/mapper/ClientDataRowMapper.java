package ru.sshell.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.sshell.model.ClientData;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author sshell
 * Date: 23.02.2020
 */
public class ClientDataRowMapper implements RowMapper<ClientData> {

    @Override
    public ClientData mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ClientData.builder().setId(rs.getLong("user_id"))
                .setBlocked();
    }
}
