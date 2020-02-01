package ru.sshell.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class WorkScheduler {
    private static final Lock lock;
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkScheduler.class);

    static {
        lock = new ReentrantLock();
    }

    private final TaskWorker taskWorker;

    @Autowired
    public WorkScheduler(TaskWorker taskWorker) {
        this.taskWorker = taskWorker;
    }

    @Scheduled(fixedRate = 30000)
    public void schedule() {
        try {
            LOGGER.debug("Start process schedule  to task work");
            if (!lock.tryLock()) {
                return;
            }
            taskWorker.work();
        } catch (Exception ex) {
            LOGGER.error("Error while scheduler work", ex);
        } finally {
            lock.unlock();
            LOGGER.debug("End process schedule  to task work");

        }

    }
}
