<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница</title>
    <script type="text/javascript"><%@include file="./js/coockieWorker.js"%> </script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
</head>
<body>
<table width="100%" height="100%">
    <tr>
        <td align="center">
            <table border="1" width="120px">
                <tr align="center">
                    <td>
                        <button name="tasks" onClick='location.href="all-tasks"'>Управление задачами</button>
                        <button name="clients" onClick='location.href="all-clients"'>Управление клиентами</button>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>