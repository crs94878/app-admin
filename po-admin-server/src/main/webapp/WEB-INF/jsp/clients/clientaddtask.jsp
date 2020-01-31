<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Назначение задачи клиенту</title>
    <script type="text/javascript"><%@include file="../js/task/TaskForClient.js"%> </script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript"><%@include file="../js/coockieWorker.js"%> </script>
</head>
<body>
<div>
    <table style="border: 1px solid black; border-collapse: collapse;">
        <tr>
            <td style="border: 1px solid black;">Выберите задачу:</td>
        </tr>
        <tr><td>
            <select id="task">
                <c:forEach var="task" items="${tasklist}">
                    <option value="${task.id}">${task.name}:${task.version}</option>
                </c:forEach>
            </select>
        </td></tr>
    </table>
    <table>
        <tr>
            <td><button name="tasks" onClick='location.href="main"'>Назад</button></td>
            <td><button onclick="addTask(${clientId})">Назначить</button></td>
        </tr>
    </table>
</div>
</body>
</html>