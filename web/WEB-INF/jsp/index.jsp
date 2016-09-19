<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
    <title>Добро пожаловать</title>
</head>
<body>
Добро пожаловать, <strong>${username}</strong>!
<br>
<br>
<s:a action="logout">Выйти</s:a>
</body>
</html>
