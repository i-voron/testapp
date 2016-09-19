<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<title>Авторизация</title>
</head>
<body>
<h2>Авторизация</h2>
<s:actionerror />
<s:if test="%{#parameters.error != null}">
    <div style="color: red">${SPRING_SECURITY_LAST_EXCEPTION.message}</div>
</s:if>
<s:if test="%{#parameters.logout != null}">
    <div style="color: green">You have been logged out successfully.</div>
</s:if>
<s:form action="login"  method="post" namespace="/">
	<s:textfield name="login" label="Логин" size="20" />
	<s:password name="password" label="Пароль" size="20" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<s:submit  value="Войти" align="center" />
</s:form>
</body>
</html>