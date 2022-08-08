package ru.sshell.service.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.sshell.model.ClientData;
import ru.sshell.model.SessionData;
import ru.sshell.model.TaskData;
import ru.sshell.model.TaskStatus;
import ru.sshell.model.dto.ClientTaskStatusDto;
import ru.sshell.model.dto.SimpleClientTaskDataDto;
import ru.sshell.model.dto.TaskPackDto;

import javax.annotation.Nullable;


@Service
public class RestService extends RootRestService {


    @Value("${service.task.url}")
    private String serviceUrl;

    @Autowired
    public RestService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @CheckAuthorisation(disable = true)
    public void checkin(ClientData clientData) {
        String endPointUrl = serviceUrl + "/api/client/checkin";
        sessionData = postRequest(endPointUrl, clientData, SessionData.class);
    }

    @Nullable
    public TaskPackDto getTaskIds() {
        String endPointUrl = serviceUrl + "/api/client/tasks";
        UriComponents url = buildUrl("clientId", sessionData.getUserId(), endPointUrl);
        return getRequest(url.toUriString(), TaskPackDto.class);
    }

    @Nullable
    public TaskData getTaskDataById(Long taskId) {
        String endPointUrl = serviceUrl + "/api/client/get-task";
        UriComponents url = buildUrl("taskId", taskId, endPointUrl);
        return getRequest(url.toUriString(), TaskData.class);
    }

    public void updateTaskStatus(TaskStatus taskStatus, Long taskId) {
        String endpointUrl = serviceUrl + "/api/client/task/status-toggle";
        ClientTaskStatusDto clientTaskStatusDto = createSimplClStatData(taskStatus, taskId);
        postRequest(endpointUrl, clientTaskStatusDto, String.class);
    }

    @NonNull
    private ClientTaskStatusDto createSimplClStatData(TaskStatus taskStatus, Long taskId) {
        ClientTaskStatusDto clientTaskStatusDto = new ClientTaskStatusDto();
        clientTaskStatusDto.setTaskStatus(taskStatus);
        SimpleClientTaskDataDto simpleClientTaskDataDto = new SimpleClientTaskDataDto();
        simpleClientTaskDataDto.setClientId(sessionData.getUserId());
        simpleClientTaskDataDto.setTaskId(taskId);
        clientTaskStatusDto.setClientTaskData(simpleClientTaskDataDto);
        return clientTaskStatusDto;
    }

}
