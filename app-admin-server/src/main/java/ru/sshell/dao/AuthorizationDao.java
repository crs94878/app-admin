package ru.sshell.dao;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.map.property.FieldMapperColumnDefinition;
import org.simpleflatmapper.reflect.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sshell.model.AdminAuthorizationDataDto;
import ru.sshell.model.SessionData;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class AuthorizationDao extends AbstractDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationDao.class);

    public AuthorizationDao(NamedParameterJdbcTemplate parameterJdbcTemplate) {
        super(parameterJdbcTemplate);
    }
    /**
     * ПОиск в БД пользователя с логин/паролем
     * @param adminAuthorizationDataDto данные для авторизации
     * @return логин пользователя есть с такимии данными авторизации он  был найден в базе
     */
    public List<Long> authorization(AdminAuthorizationDataDto adminAuthorizationDataDto) {
        LOGGER.debug("Start find client: {}", adminAuthorizationDataDto);
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("login", adminAuthorizationDataDto.getLogin())
                .addValue("password", adminAuthorizationDataDto.getPassword());
        return parameterJdbcTemplate.query(AuthorizationDaoQuery.ADMIN_AUTH_QUERY, mapSource, ADMIN_LOGIN_COUNT);
    }

    public List<SessionData> loadSessionDataByToken(String token) {
        LOGGER.debug("Get session by token: {}", token);
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
        .addValue("token", token);
        return parameterJdbcTemplate.query(AuthorizationDaoQuery.GET_SESSION_DATA_QUERY, mapSource, SESSION_MAPPER);
    }

    public List<SessionData> loadSessionDataByLoginId(Long id) {
        LOGGER.debug("Get session by user Id: {}", id);
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("userId", id);
        return parameterJdbcTemplate.query(AuthorizationDaoQuery.GET_SESSION_BY_LOGINID, mapSource, SESSION_MAPPER);
    }

    public void addOrUpdateSessionData(SessionData sessionData) {
        LOGGER.debug("Add session Data for : {}", sessionData);
        MapSqlParameterSource mapSource =  new MapSqlParameterSource()
                .addValue("token", sessionData.getToken())
                .addValue("userId", sessionData.getUserId())
                .addValue("dttm_rec", new Timestamp(Instant.now().toEpochMilli()))
                .addValue("dttm_exp", sessionData.getExpDateTime());
        parameterJdbcTemplate.update(AuthorizationDaoQuery.ADD_OR_UPDATE_AUTH_QUERY, mapSource);
    }


    public void removeOldSessions() {
        LOGGER.debug("Remove old sessions");
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("dttm_exp", new Date());
        parameterJdbcTemplate.update(AuthorizationDaoQuery.DELETE_OLD_SESSIONS, mapSource);
    }

    /**
     * Внутренний класс с запросами в БД
     */
    private static final class AuthorizationDaoQuery {
        private static final String ADMIN_AUTH_QUERY = "SELECT user_id FROM app_admin_schema.logins WHERE login = :login AND password = :password";
        private static final String GET_SESSION_DATA_QUERY = "SELECT s.*, l.user_type" +
                " FROM app_admin_schema.sessions s " +
                " JOIN app_admin_schema.logins l ON l.user_id = s.user_id " +
                " WHERE s.token = :token";
        private static final String GET_SESSION_BY_LOGINID = "SELECT s.*, l.user_type " +
                " FROM app_admin_schema.sessions s " +
                " JOIN app_admin_schema.logins l ON l.user_id = s.user_id " +
                " WHERE s.user_id = :userId";

        private static final String ADD_OR_UPDATE_AUTH_QUERY = "INSERT INTO app_admin_schema.sessions" +
                " (token, user_id, dttm_rec, dttm_expired) " +
                " VALUES " +
                " (:token, :userId, :dttm_rec, :dttm_exp)" +
                " ON CONFLICT (user_id)" +
                " DO " +
                " UPDATE " +
                " SET " +
                " dttm_expired = :dttm_exp " +
                " WHERE token = :token";

        private static final String DELETE_OLD_SESSIONS = "DELETE FROM app_admin_schema.sessions " +
                "WHERE dttm_exp < :dttm_exp " +
                "AND sessionType = 'CLIENT' " +
                "clientId  in (SELECT clientId FROM " +
                "(SELECT clientId, COUNT(*) FROM sessions " +
                "WHERE status = 'In queue' HAVING COUNT(*) = 0";

    }
}
