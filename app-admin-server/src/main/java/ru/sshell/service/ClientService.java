package ru.sshell.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sshell.dao.ClientDao;
import ru.sshell.exception.OperationException;
import ru.sshell.exception.ProcessBrokenException;
import ru.sshell.model.ClientData;
import ru.sshell.model.dto.MachineDataDto;
import ru.sshell.model.dto.SimpleClientDto;

import java.util.List;

/**
 * Сервис для работы с задачами
 */

@Service
public class ClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    private final ClientDao clientDao;

    @Autowired
    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public void addClient(ClientData clientData) {
        clientDao.addClient(clientData);
    }

    public List<ClientData> findClientByHostName(String hostname) {
        return clientDao.getClient(hostname);

    }

    public ClientData createNewClient(MachineDataDto machineDataDto) {
        try {
            return ClientData
                    .builder()
                    .setId(clientDao.getNewClientId())
                    .setBlocked(true)
                    .setHostname(machineDataDto.getHostname())
                    .setOs(machineDataDto.getOs())
                    .setOsType(machineDataDto.getOsType())
                    .setMacAddr(machineDataDto.getMacAddr())
                    .build();
        } catch (OperationException opEx) {
            LOGGER.error("Error while get id for new Client", opEx);
            throw new ProcessBrokenException(HttpStatus.SERVICE_UNAVAILABLE, "Request later");
        }
    }

    public ClientData findClientByHostName(Long id) {
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
