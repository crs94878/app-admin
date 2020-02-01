package ru.sshell.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sshell.dao.ClientDao;
import ru.sshell.model.ClientData;
import ru.sshell.model.dto.SimpleClientDto;

import java.util.List;

/**
 * Сервис для работы с задачами
 */

@Service
public class ClientService {
    private ClientDao clientDao;
    @Autowired
    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public void addClient(ClientData clientData) {
        clientDao.addClient(clientData);
    }

    public List<ClientData> getClient(String hostname) {
        return clientDao.getClient(hostname);

    }

    public ClientData getClient(Long id) {
        return clientDao.getClient(id);
    }

    @Transactional
    public void blockClient(SimpleClientDto client) {
        clientDao.updateClientState(client);
    }

    public List<ClientData> getAllClients()
    {
        return clientDao.getAllClients();
    }

    public void deleteById(Long id) {
        clientDao.deleteById(id);
    }

    public void deleteByHostname(String hostname) {
        clientDao.deleteByHostname(hostname);
    }
}
