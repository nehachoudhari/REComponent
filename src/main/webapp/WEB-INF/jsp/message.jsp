<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Persuasion Message Management</title>
</head>
<body>
<h1>Persuasion Messages</h1>
<form:form action="message.do" method="POST" commandName="message">
	<table>
		<tr>
			<td>Message Id</td>
			<td><form:input path="msgId" /></td>
		</tr>
		<tr>
			<td>Message Text</td>
			<td><form:input path="text" /></td>
		</tr>
		<tr>
			<td>Tags</td>
			<td><form:input path="tags" /></td>
		</tr>
		
		<tr>
			<td colspan="2">
				<input type="submit" name="action" value="Add" />
				<input type="submit" name="action" value="Edit" />
				<input type="submit" name="action" value="Delete" />
				<input type="submit" name="action" value="Search" />
			</td>
		</tr>
	</table>
</form:form>
<br>
<table border="1">
	<th>ID</th>
	<th>Text</th>
	<th>Tags</th>
	<c:forEach items="${messageList}" var="message">
		<tr>
			<td>${message.msgId}</td>
			<td>${message.text}</td>
			<td>${message.tags}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>