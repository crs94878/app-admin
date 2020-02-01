package ru.sshell.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@EnableScheduling
@Service
public class AppScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppScheduler.class);

    private static final Lock scheduleLocker = new ReentrantLock();

    private final AuthorizationService authorizationService;

    @Autowired
    public AppScheduler(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

//    @Scheduled(fixedRate = 60000)
    public void removeOldSessionScheduler() {
        LOGGER.debug("Start process for remove old sessions");
        try {
            if (!scheduleLocker.tryLock()) {
                LOGGER.debug("Thread is lock");
                return;
            }
            authorizationService.removeOldSessions();
        } catch (Exception ex) {
            LOGGER.error("Error while remove old sessions", ex);
        }
    }
}
