package ru.sshell.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;
import ru.sshell.dao.AuthorizationDao;
import ru.sshell.dao.ClientDao;
import ru.sshell.dao.TaskDao;
import ru.sshell.service.AuthorizationService;
import ru.sshell.service.ClientService;
import ru.sshell.service.TaskService;

/**
 * @author belka
 * Date: 2020.11.19
 */
@Configuration
public class ServiceConfiguration {

    @Autowired
    private AuthorizationDao authorizationDao;
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Bean
    public AuthorizationService authorizationService() {
        return new AuthorizationService(authorizationDao, clientService(), transactionTemplate);
    }

    @Bean
    public ClientService clientService() {
        return new ClientService(clientDao);
    }

    @Bean
    public TaskService taskService() {
        return new TaskService(taskDao);
    }
}
