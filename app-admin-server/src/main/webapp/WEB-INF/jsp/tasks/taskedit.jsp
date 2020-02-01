<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Редактировать задачу</title>
    <script type="text/javascript"><%@include file="../js/task/edit-task.js"%></script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript"><%@include file="../js/coockieWorker.js"%> </script>
</head>
<body>
<table>
    <tr>
        <td>
            <form:form modelAttribute="task">
                <table style="border: 1px solid black; border-collapse: collapse;">
                    <tr>
                        <td style="border: 1px solid black;"><form:label path="id">id:</form:label> </td>
                        <td style="border: 1px solid black;"><form:input id="id" path="id" readonly="true"/></td>
                    </tr><tr>
                        <td style="border: 1px solid black;"><form:label path="name">Имя:</form:label></td>
                        <td style="border: 1px solid black;"><form:input id="name" path="name"/></td>
                    </tr><tr>
                    <td style="border: 1px solid black;"><form:label path="taskProcessType">Тип:</form:label></td>
                    <td style="border: 1px solid black;"><form:select path="taskProcessType">
                        <form:option value="BAT">Bat</form:option>
                        <form:option value="POWERSHELL">Powershell</form:option>
                        <form:option value="PROGRAM">Program</form:option>
                        <form:option value="SH">Sh</form:option>
                    </form:select></td>
                </tr><tr>
                    <td style="border: 1px solid black;"><form:label path="version">Версия:</form:label></td>
                    <td style="border: 1px solid black;"><form:input id="version" path="version"/></td>
                </tr><tr>
                    <td style="border: 1px solid black;"><form:label path="os">OS:</form:label></td>
                    <td style="border: 1px solid black;"><form:select path="os">
                        <form:option value="WINDOWS">Windows</form:option>
                        <form:option value="LINUX">Linux</form:option>
                        <form:option value="MACOS">MacOS</form:option>
                    </form:select></td>
                </tr><tr>
                    <td style="border: 1px solid black;"><form:label path="osType">OS arch:</form:label></td>
                    <td style="border: 1px solid black;"><form:select path="osType">
                        <form:option value="X64">x64</form:option>
                        <form:option value="X86">x86</form:option>
                    </form:select></td>
                </tr><tr>
                    <td style="border: 1px solid black;"><form:label path="pathToRunFile">Run File:</form:label></td>
                    <td style="border: 1px solid black;"><form:input id="pathToRunFile" path="pathToRunFile"/></td>
                </tr><tr>
                    <td style="border: 1px solid black;"><form:label path="torrentFile">BASE64 Torrent:</form:label></td>
                    <td style="border: 1px solid black;"><form:input type="file" id="torrentFile" path="torrentFile"/></td>
                </tr><tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr><tr>
                    <td colspan="2" align="center"><input type="button" value="Сохранить" onclick="edittask()"></td>
                </tr>
                </table>
            </form:form>
        </td>
    </tr>
    <tr>
        <td><button name="tasks" onClick='location.href="all-tasks"'>Назад</button></td>
    </tr>
</table>
</body>
</html>
