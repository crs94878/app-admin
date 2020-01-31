<%@ page import="ru.sshell.service.TaskService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Задачи для клиента ${clientId} </title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript"><%@include file="../js/task/TaskForClient.js"%></script>
    <script type="text/javascript"><%@include file="../js/coockieWorker.js"%> </script>
</head>
<body>
<div>
    <table style="border: 1px solid black; border-collapse: collapse;">
        <tr>
            <td style="border: 1px solid black;">Номер:</td>
            <td style="border: 1px solid black;">Имя:</td>
            <td style="border: 1px solid black;">Тип:</td>
            <td style="border: 1px solid black;">Версия:</td>
            <td style="border: 1px solid black;">OS:</td>
            <td style="border: 1px solid black;">OS arch:</td>
            <td style="border: 1px solid black;">Run File:</td>
            <td style="border: 1px solid black;">Torrent:</td>
            <td style="border: 1px solid black;">Status:</td>
            <td>&nbsp;</td>
        </tr>
        <c:forEach var="task" items="${tasklist}">
            <tr>
                <td style="border: 1px solid black;">${task.taskData.id}</td>
                <td style="border: 1px solid black;">${task.taskData.name}</td>
                <td style="border: 1px solid black;">${task.taskData.taskProcessType.type}</td>
                <td style="border: 1px solid black;">${task.taskData.version}</td>
                <td style="border: 1px solid black;">${task.taskData.os.os}</td>
                <td style="border: 1px solid black;">${task.taskData.osType.osType}</td>
                <td style="border: 1px solid black;">${task.taskData.pathToRunFile}</td>
                <td style="border: 1px solid black; text-align: center">${task.taskData.torrentFile != null ? "<img src=\"https://iconizer.net/files/Puck_icons_pack_II/thumb/128/uTorrent.png\" width=\"30\" height=\"30\">" : ""}</td>
                <td style="border: 1px solid black;">${task.status}</td>
                <td style="border: 1px solid black;"><button onclick="deleteTask(${task.taskData.id}, ${clientId})">Отменить</button></td>
            </tr>
        </c:forEach>
    </table>
    <table>
        <tr>
            <td><button name="tasks" onClick='location.href="all-clients"'>Назад</button></td>
        </tr>
    </table>
</div>
</body>
</html>