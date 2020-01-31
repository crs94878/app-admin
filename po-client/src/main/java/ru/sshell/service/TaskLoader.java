package ru.sshell.service;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.sshell.model.TaskData;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

@Service
public class TaskLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskLoader.class);

    public void loadTorrent(TaskData task) throws IOException, NoSuchAlgorithmException {
        if (task == null) {
            return;
        }
        File dir = new File("./catch/tasks/" + task.getId());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Client client = new Client(InetAddress.getLocalHost(), new SharedTorrent(Base64.getDecoder().decode(task.getTorrentFile().getBytes("UTF8")), dir));
        client.download();
        client.waitForCompletion();
        run(task, dir);
    }

    private void run(TaskData task, File dir) throws IOException {
        if (task == null) {
            return;
        }
        String command = "";
        switch (task.getTaskProcessType()) {
            case BAT:
                if (task.getPathToRunFile().endsWith(".bat") || task.getPathToRunFile().endsWith(".cmd"))
                    command = "cmd.exe /c \"" + dir.getAbsolutePath() + "\\" + task.getPathToRunFile() + "\"";
                break;
            case POWERSHELL:
                if (task.getPathToRunFile().endsWith(".ps1"))
                    command = "cmd.exe /c powershell -command - < \"" + dir.getAbsolutePath() + "\\" + task.getPathToRunFile() + "\"";
                break;
            case SH:
                if (task.getPathToRunFile().endsWith(".sh"))
                    // TODO command for Linux;
                    break;
            case PROGRAM:
                command = "cmd.exe /c \"" + dir.getAbsolutePath() + "\\" + task.getPathToRunFile() + "\"";
                break;
        }
        if (!command.isEmpty() && !Objects.isNull(command)) {
            Process process = Runtime.getRuntime().exec(command);
        }
    }
}
