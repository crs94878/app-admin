package ru.sshell.dao;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.map.property.FieldMapperColumnDefinition;
import org.simpleflatmapper.reflect.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sshell.exception.OperationException;
import ru.sshell.model.ClientData;
import ru.sshell.model.dto.SimpleClientDto;
import ru.sshell.model.enums.OS;
import ru.sshell.model.enums.OSType;

import java.sql.ResultSet;
import java.util.List;

public class ClientDao extends AbstractDao{

    private static final RowMapper<Long> LONG_ROW_MAPPER = JdbcTemplateMapperFactory.newInstance()
            .newRowMapper(Long.class);

    @Autowired
    public ClientDao(NamedParameterJdbcTemplate parameterJdbcTemplate) {
        super(parameterJdbcTemplate);
    }


    public long getNewClientId() {
        return parameterJdbcTemplate.query("", LONG_ROW_MAPPER)
                .stream()
                .findFirst()
                .orElseThrow(
                        () ->  new OperationException("Cant get new client id")
                );
    }

    public int addClient(ClientData client) {
        MapSqlParameterSource mapSource =  new MapSqlParameterSource()
                .addValue("hostName", client.getHostname())
                .addValue("os", client.getOs().getName())
                .addValue("osType", client.getOsType().getType())
                .addValue("macAddr", client.getMacAddr())
                .addValue("isBlocked", client.getBlocked());
        return parameterJdbcTemplate.update(ClientDaoQuery.ADD_CLIENT_QUERY, mapSource);
    }

    public ClientData getClient(Long id) {
        MapSqlParameterSource mapSource =  new MapSqlParameterSource()
                .addValue("id", id);
        return parameterJdbcTemplate.query(ClientDaoQuery.GET_CLIENT_BY_ID, mapSource, clientDataListRowMapper).get(0);
    }

    public List<ClientData> getClient(String hostname) {
        MapSqlParameterSource mapSource =  new MapSqlParameterSource()
                .addValue("hostname", hostname);
        return parameterJdbcTemplate.query(ClientDaoQuery.GET_CLIENT_QUERY, mapSource, clientDataListRowMapper);
    }


    public List<ClientData> getAllClients() {
        return parameterJdbcTemplate.query(ClientDaoQuery.GET_ALL_CLIENTS_QUERY, clientDataListRowMapper);
    }

    public int deleteById(Long id) {
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("id", id);
        return parameterJdbcTemplate.update(ClientDaoQuery.DELETE_CLIENT_BY_ID, mapSource);
    }

    public int deleteByHostname(String hostname) {
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("hostname", hostname);
        return parameterJdbcTemplate.update(ClientDaoQuery.DELETE_CLIENT_BY_HOSTNAME, mapSource);
    }

    public int updateClientState(SimpleClientDto client) {
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("id", client.getId())
                .addValue("blocked", client.getBlocked() ? 1 : 0);
        return parameterJdbcTemplate.update(ClientDaoQuery.UPDATE_CLIENT_STATE_BY_ID, mapSource);

    }

    /**
     * Внутренний класс с запросами в БД
     */
    private static final class ClientDaoQuery {
        private static final String GET_CLIENT_BY_ID = "SELECT * FROM app_admin_schema.logins WHERE user_id = :user_id";
        private static final String GET_CLIENT_QUERY = "SELECT * FROM clients WHERE hostname = :hostname";
        private static final String GET_ALL_CLIENTS_QUERY = "SELECT * FROM clients";
        private static final String ADD_CLIENT_QUERY = "INSERT INTO clients(hostname, os, osType, macAddr) " +
                "VALUES (:hostName, :os, :osType, :macAddr)";

        private static final String DELETE_CLIENT_BY_ID = "DELETE FROM clients WHERE id = :id";
        private static final String UPDATE_CLIENT_STATE_BY_ID = "UPDATE clients" +
                " SET blocked = :blocked " +
                "WHERE id = :id";
        private static final String DELETE_CLIENT_BY_HOSTNAME = "DELETE clients WHERE hostname = ?";

    }
}
