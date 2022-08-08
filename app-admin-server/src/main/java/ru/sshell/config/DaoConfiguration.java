package ru.sshell.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.sshell.dao.AuthorizationDao;
import ru.sshell.dao.ClientDao;
import ru.sshell.dao.TaskDao;
import ru.sshell.service.AuthorizationService;

/**
 * Конфигурация для ДАО компонентов
 * @author belka
 * Date: 2020.11.19
 */
@Configuration
public class DaoConfiguration {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Bean
    public ClientDao clientDao() {
        return new ClientDao(namedParameterJdbcTemplate);
    }

    @Bean
    public AuthorizationDao authorizationDao() {
        return new AuthorizationDao(namedParameterJdbcTemplate);
    }

    @Bean
    public TaskDao taskDao() {
        return new TaskDao(namedParameterJdbcTemplate);
    }
}
