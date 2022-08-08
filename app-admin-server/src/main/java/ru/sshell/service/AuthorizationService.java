package ru.sshell.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import ru.sshell.dao.AuthorizationDao;
import ru.sshell.exception.ClientAuthorizationException;
import ru.sshell.exception.ResourceBlockedException;
import ru.sshell.model.AdminAuthorizationDataDto;
import ru.sshell.model.ClientData;
import ru.sshell.model.SessionData;
import ru.sshell.model.dto.MachineDataDto;
import ru.sshell.util.TokenGenerator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Сервис для выполнения авторизации
 */
public class AuthorizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationService.class);
    private static final int TOKEN_UPDATE_EXP_HOUR = 24;

    private final AuthorizationDao authorizationDao;
    private final ClientService clientService;
    private final TransactionTemplate transactionTemplate;

    public AuthorizationService(AuthorizationDao authorizationDao,
                                ClientService clientService,
                                TransactionTemplate transactionTemplate) {
        this.authorizationDao = authorizationDao;
        this.clientService = clientService;
        this.transactionTemplate = transactionTemplate;
    }

    @Transactional
    public SessionData adminAuth(AdminAuthorizationDataDto authData) {
        try {
            LOGGER.debug("Start authorization by admin console: {}", authData);
            Long result = authorizationDao.authorization(authData).stream()
                    .findFirst()
                    .orElseThrow(() ->
                            new ResourceBlockedException(HttpStatus.NOT_FOUND, "Admin not found in DB")
                    );
            SessionData sessionData = authorizationDao.loadSessionDataByLoginId(result)
                    .stream()
                    .findFirst()
                    .orElseGet(
                            () -> {
                                LOGGER.debug("Not found session data, will be login");
                                return createSessionData(result, SessionData.SessionType.ADMIN);
                            });
            transactionTemplate.execute(
                    (transactionStatus) -> {
                        addOrUpdateSessionData(sessionData);
                        return transactionStatus;
                    }
            );
            return sessionData;
        } catch (Exception ex) {
            LOGGER.error("Error while admin auth", ex);
            throw new ResourceBlockedException(HttpStatus.FORBIDDEN, "Admin not found in DB");
        }
    }

    @Transactional
    public SessionData clientCheckIn(MachineDataDto authData) {
        LOGGER.debug("Start authorization by client: {}", authData);
        ClientData client = clientService.findClientByHostName(
                authData.getHostname()
        )
                .stream()
                .findFirst()
                .orElseGet(
                        () -> {
                            LOGGER.error("Client not found by data: {}", authData);
                            return clientService.createNewClient(
                                    authData
                            );
                        }
                );
        checkClientBlocked(client);
        return checkInSessionData(
                authorizationDao.loadSessionDataByLoginId(client.getId()),
                client
        );
    }

    private void checkClientBlocked(ClientData client) {
        if (client.getBlocked()) {
            LOGGER.error("Blocked process for client: {}", client);
            throw new ClientAuthorizationException(HttpStatus.FORBIDDEN, "Client tasks can't start work," +
                    " because he is blocked");
        }
    }

    private SessionData checkInSessionData(List<SessionData> sessionDataList, ClientData client) {
        SessionData sessionData = sessionDataList.stream()
                .findFirst()
                .orElseGet(
                        () -> createSessionData(client.getId(), SessionData.SessionType.CLIENT)
                );
        transactionTemplate.execute(
                (transactionStatus) -> {
                    addOrUpdateSessionData(sessionData);
                    return transactionStatus;
                }
        );
        LOGGER.debug("Added auth session: {}", sessionData);
        return sessionData;
    }

    /**
     * Создать данные по сессии
     * @param userId  id пользователя
     * @param sessionType
     * @return
     */
    private SessionData createSessionData(long userId, SessionData.SessionType sessionType) {
        return SessionData.builder()
                .setToken(TokenGenerator.generateToken())
                .setUserId(userId)
                .setSessionType(sessionType)
                .setExpDateTime(Instant.now())
                .build();
    }

    @Transactional
    public void addOrUpdateSessionData(SessionData sessionData) {
        authorizationDao.addOrUpdateSessionData(
                SessionData
                        .builder()
                        .of(sessionData)
                        .setExpDateTime(spotDate())
        .build());
    }


    private Instant spotDate() {
        Instant timeInstant = Instant.now();
        return timeInstant.plus(TOKEN_UPDATE_EXP_HOUR, ChronoUnit.HOURS);
    }

    public List<SessionData> loadSessionDataByToken(String token) {
        return authorizationDao.loadSessionDataByToken(token);
    }

    @Transactional
    public void removeOldSessions() {
        authorizationDao.removeOldSessions();
    }
}
