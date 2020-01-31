package ru.sshell.dao;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.map.property.FieldMapperColumnDefinition;
import org.simpleflatmapper.reflect.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.sshell.model.AdminAuthorizationData;
import ru.sshell.model.SessionData;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class AuthorizationDao extends AbstractDao {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationDao.class);

    private static final int TOKEN_UPDATE_EXP_HOUR = 24;

    private static final String ADMIN_AUTH_QUERY = "SELECT id FROM logins WHERE login = :login AND password = :password";
    private static final String GET_SESSION_DATA_QUERY = "SELECT * FROM sessions WHERE token = :token";
    private static final String GET_SESSION_BY_LOGINID = "SELECT * FROM sessions WHERE clientId = :clientId";
    private static final String ADD_AUTH_QUERY = "INSERT INTO sessions(token, clientId, dttm_exp, sessionType) VALUES (:token, :clientId, :dttm_exp, :sessionType)";
    private static final String UPDATE_SESSION_DATA = "UPDATE sessions " +
            "SET " +
            "dttm_exp = :dttm_exp " +
            "WHERE token = :token";

    private static final String DELETE_OLD_SESSIONS = "DELETE FROM sessions " +
            "WHERE dttm_exp < :dttm_exp " +
            "AND sessionType = 'CLIENT' " +
            "clientId  in (SELECT clientId FROM " +
            "(SELECT clientId, COUNT(*) FROM sessions " +
            "WHERE status = 'In queue' HAVING COUNT(*) = 0";

    private static final RowMapper<Long> ADMIN_LOGIN_COUNT = JdbcTemplateMapperFactory.newInstance()
            .newRowMapper(Long.class);
    private static final RowMapper<SessionData> SESSION_MAPPER = JdbcTemplateMapperFactory.newInstance()
            .addAlias("dttm_exp", "expDate")
            .addColumnDefinition("sessionType",
                    FieldMapperColumnDefinition.customGetter((Getter<ResultSet, SessionData.SessionType>) rs ->
                            SessionData.SessionType.getTypeByCode(rs.getInt("sessionType"))))
            .ignorePropertyNotFound().newRowMapper(SessionData.class);


    /**
     * Метод лезет в базу и ищет там пользователя с таким логин/паролем
     * @param adminAuthorizationData данные для авторизации
     * @return логин пользователя есть с такимии данными авторизации он  был найден в базе
     */
    public List<Long> authorization(AdminAuthorizationData adminAuthorizationData) {
        logger.debug("Start find client: {}", adminAuthorizationData);
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("login", adminAuthorizationData.getLogin())
                .addValue("password", adminAuthorizationData.getPassword());
        return parameterJdbcTemplate.query(ADMIN_AUTH_QUERY, mapSource, ADMIN_LOGIN_COUNT);
    }

    public void addSessionData(SessionData sessionData) {
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("token", sessionData.getToken())
                .addValue("clientId", sessionData.getClientId())
                .addValue("dttm_exp", spotDate())
                .addValue("sessionType", sessionData.getSessionType().getCode());
        parameterJdbcTemplate.update(ADD_AUTH_QUERY, mapSource);
    }

    public List<SessionData> loadSessionDataByToken(String token) {
        logger.debug("Get session by token: {}", token);
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
        .addValue("token", token);
        return parameterJdbcTemplate.query(GET_SESSION_DATA_QUERY, mapSource, SESSION_MAPPER);
    }

    public List<SessionData> loadSessionDataByLoginId(Long id) {
        logger.debug("Get session by loginId: {}", id);
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("clientId", id);
        return parameterJdbcTemplate.query(GET_SESSION_BY_LOGINID, mapSource, SESSION_MAPPER);
    }

    public void updateSessionData(String token) {
        logger.debug("Update session Data by token: {}", token);
        MapSqlParameterSource mapSource =  new MapSqlParameterSource()
                .addValue("token", token)
                .addValue("dttm_exp", spotDate());
        parameterJdbcTemplate.update(UPDATE_SESSION_DATA, mapSource);
    }

    private Date spotDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, TOKEN_UPDATE_EXP_HOUR);
        return cal.getTime();
    }

    public void removeOldSessions() {
        logger.debug("Remove old sessions");
        MapSqlParameterSource mapSource = new MapSqlParameterSource()
                .addValue("dttm_exp", new Date());
        parameterJdbcTemplate.update(DELETE_OLD_SESSIONS, mapSource);
    }
}
