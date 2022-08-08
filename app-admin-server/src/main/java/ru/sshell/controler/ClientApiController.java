package ru.sshell.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sshell.model.*;
import ru.sshell.model.dto.MachineDataDto;
import ru.sshell.model.dto.ClientTaskStatusDto;
import ru.sshell.model.dto.TaskPackDto;
import ru.sshell.service.TaskService;
import ru.sshell.service.AuthorizationService;

@Controller
@RequestMapping("/api/client")
public class ClientApiController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientApiController.class);

    private final AuthorizationService authorizationService;
    private final TaskService taskService;

    @Autowired
    public ClientApiController(AuthorizationService authorizationService, TaskService taskService) {
        this.authorizationService = authorizationService;
        this.taskService = taskService;
    }

    @PostMapping(path = "/checkin")
    @ResponseBody
    public SessionData clientCheckin(@RequestBody MachineDataDto clientData) {
        LOGGER.debug("Received request for checkin: {}", clientData);
        SessionData sessionData = authorizationService.clientCheckIn(clientData);
        LOGGER.debug("Get response: {}", sessionData);
        return sessionData;
    }
    @GetMapping("/tasks")
    @ResponseBody
    public TaskPackDto getClientTaskList(@RequestParam(name = "clientId", required = true) Long clientId) {
        LOGGER.debug("Received request for get clients tasks");
        TaskPackDto taskPackDto = new TaskPackDto(taskService.getActiveTasksByClientID(clientId));
        LOGGER.debug("Get response data: {}", taskPackDto);
        return taskPackDto;
    }


    @GetMapping("get-task")
    @ResponseBody
    public TaskData getTaskById(@RequestParam(name = "taskId", required = true) Long taskId) {
        return taskService.getTaskById(taskId);
    }

    @PostMapping("/task/status-toggle")
    @ResponseBody
    public String toggleTaskStatus(@RequestBody ClientTaskStatusDto clientTaskStatusDto) {
        LOGGER.debug("Received request for toggle status: {}", clientTaskStatusDto);
        taskService.updateStatus(clientTaskStatusDto);
        return "Success";
    }
}
