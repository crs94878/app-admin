package ru.sshell.service;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sshell.dao.AuthorizationDao;
import ru.sshell.exception.ClientAuthorizationException;
import ru.sshell.model.AdminAuthorizationData;
import ru.sshell.model.ClientData;
import ru.sshell.model.SessionData;
import ru.sshell.util.TokenGenerator;

import java.util.Date;
import java.util.List;

/**
 * Сервис для выполнения авторизации
 */
@Service
public class AuthorizationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    @Autowired
    private AuthorizationService authorizationService;
    private final AuthorizationDao authorizationDao;
    private final ClientService clientService;


    @Autowired
    public AuthorizationService(AuthorizationDao authorizationDao, ClientService clientService) {
        this.authorizationDao = authorizationDao;
        this.clientService = clientService;
    }

    @Transactional
    public SessionData adminAuth(AdminAuthorizationData authData) {
        logger.debug("Start authorization by admin console: {}", authData);
        List<Long> result = authorizationDao.authorization(authData);
        if (CollectionUtils.isEmpty(result)) {
            throw new ClientAuthorizationException(HttpStatus.NOT_FOUND, "Admin not found in DB");
        }
        List<SessionData> sessionDataList = authorizationDao.loadSessionDataByLoginId(result.get(0));
        if (CollectionUtils.isEmpty(sessionDataList)) {
            SessionData sessionData = createSessionData(result.get(0), SessionData.SessionType.ADMIN);
            authorizationDao.addSessionData(sessionData);
            return sessionData;
        }
        SessionData sessionData = sessionDataList.get(0);
        authorizationService.updateSessionData(sessionData.getToken());
        return sessionData;
    }
    /*
     * Метод
     *
     */
    @Transactional
    public SessionData clientCheckIn(ClientData authData) {
        logger.debug("Start authorization by client: {}", authData);
        List<ClientData> clientDataList = clientService.getClient(authData.getHostname());
        checkFoundClient(clientDataList, authData);
        ClientData client = clientDataList.get(0);
        checkClientBlocked(client);
        List<SessionData> sessionDataList = authorizationDao.loadSessionDataByLoginId(client.getId());
        SessionData sessionData = checkSessionData(sessionDataList, client);
        logger.debug("Return auth session: {}", sessionData);
        return sessionData;
    }

    private void checkFoundClient(List<ClientData> clientDataList, ClientData authData) {
        if (CollectionUtils.isEmpty(clientDataList)) {
            clientService.addClient(authData);
            logger.error("Client not found by data: {}", authData);
            throw new ClientAuthorizationException(HttpStatus.UNAUTHORIZED, "Client tasks can't start work," +
                    " because he is unathorized");
        }
    }

    private void checkClientBlocked(ClientData client) {
        if (client.getBlocked()) {
            logger.error("Blocked process for client: {}", client);
            throw new ClientAuthorizationException(HttpStatus.FORBIDDEN, "Client tasks can't start work," +
                    " because he is blocked");
        }
    }

    private SessionData checkSessionData(List<SessionData> sessionDataList, ClientData client) {
        SessionData sessionData;
        if (CollectionUtils.isNotEmpty(sessionDataList)) {
            sessionData = sessionDataList.get(0);
            authorizationService.updateSessionData(sessionData.getToken());
        } else {
            sessionData =  createSessionData(client.getId(), SessionData.SessionType.CLIENT);
            authorizationDao.addSessionData(sessionData);
        }
        return sessionData;
    }

    private SessionData createSessionData(long clientId, SessionData.SessionType sessionType) {
        SessionData sessionData = new SessionData();
        sessionData.setToken(TokenGenerator.generateToken());
        sessionData.setClientId(clientId);
        sessionData.setSessionType(sessionType);
        sessionData.setExpDate(new Date());
        return sessionData;
    }

    @Transactional
    public void updateSessionData(String token) {
        authorizationDao.updateSessionData(token);
    }

    public List<SessionData> loadSessionDataByToken(String token) {
        return authorizationDao.loadSessionDataByToken(token);
    }

    @Transactional
    public void removeOldSessions() {
        authorizationDao.removeOldSessions();
    }
}
