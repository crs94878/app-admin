<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Управления задачами</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
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
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <c:forEach var="task" items="${tasklist}">
                    <tr>
                        <td style="border: 1px solid black;">${task.id}</td>
                        <td style="border: 1px solid black;">${task.name}</td>
                        <td style="border: 1px solid black;">${task.taskProcessType.type}</td>
                        <td style="border: 1px solid black;">${task.version}</td>
                        <td style="border: 1px solid black;">${task.os.os}</td>
                        <td style="border: 1px solid black;">${task.osType.osType}</td>
                        <td style="border: 1px solid black;">${task.pathToRunFile}</td>
                        <td style="border: 1px solid black; text-align: center">${task.torrentFile != null ? "<img src=\"https://iconizer.net/files/Puck_icons_pack_II/thumb/128/uTorrent.png\" width=\"30\" height=\"30\">" : ""}</td>
                        <td style="border: 1px solid black;"><spring:form method="POST" action="delete-task"><button name="id" value="${task.id}">Удалить</button></spring:form></td>
                        <td style="border: 1px solid black;"><spring:form method="GET"  action="edit-task-page"><button name="id" value="${task.id}">Изменить</button></spring:form></td>
                        <td style="border: 1px solid black;"><spring:form method="GET"  action="task-clients"><button name="taskId" value="${task.id}">Список клиентов</button></spring:form></td>
                    </tr>
                </c:forEach>
            </table>
        <table>
            <tr>
                <td><button name="tasks" onClick='location.href="main"'>Назад</button></td>
                <td><button onclick='location.href="create-task-page"'>Создать </button></td>
            </tr>
        </table>
</div>
</body>
</html>