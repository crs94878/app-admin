<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Клиенты для задачи</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript"><%@include file="../js/task/TaskForClient.js"%></script>
    <script type="text/javascript"><%@include file="../js/coockieWorker.js"%> </script>
</head>
<body>
<div>
    <table style="border: 1px solid black; border-collapse: collapse;">
        <tr>
            <td style="border: 1px solid black;">Номер:</td>
            <td style="border: 1px solid black;">Имя хоста:</td>
            <td style="border: 1px solid black;">OS:</td>
            <td style="border: 1px solid black;">OS arch:</td>
            <td style="border: 1px solid black;">Mac:</td>
            <td style="border: 1px solid black;">Task Status:</td>
            <td>&nbsp;</td>
        </tr>
        <c:forEach var="client" items="${clientTaskStatusList}">
            <tr>
                <td style="border: 1px solid black;">${client.clientData.id}</td>
                <td style="border: 1px solid black;">${client.clientData.hostname}</td>
                <td style="border: 1px solid black;">${client.clientData.os.os}</td>
                <td style="border: 1px solid black;">${client.clientData.osType.osType}</td>
                <td style="border: 1px solid black;">${client.clientData.macAddr}</td>
                <td style="border: 1px solid black;">${client.taskStatus}</td>
                <td style="border: 1px solid black;"><button onclick="deleteClient(${taskId}, ${client.clientData.id})">Отменить</button></td>
            </tr>
        </c:forEach>
    </table>
    <table>
        <tr>
            <td><button name="tasks" onClick='location.href="all-tasks"'>Назад</button></td>
        </tr>
    </table>
</div>
</body>
</html>