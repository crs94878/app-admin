<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавить пользователя</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript"><%@include file="./js/Auth.js"%>
    </script>
    <script type="text/javascript"><%@include file="./js/coockieWorker.js"%>
    </script>
</head>
<body>
<table width="100%" height="100%">
    <tr>
        <td align="center">
            <table border="1" width="120px">
                <tr align="center">
                    <td>Имя:</td>
                    <td><input id="login" type="text" name="login"></td>
                </tr>
                <tr align="center">
                    <td>Пароль:</td>
                    <td><input id="password" type="password" name="password"></td>
                </tr>
                <tr align="center">
                    <td>
                        <button name="auth" onClick="authorisation()">Отправить</button>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>