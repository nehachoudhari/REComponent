<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Success Stories Management</title>
</head>
<body>
<h1>Success Stories</h1>
<form:form action="story.do" method="POST" commandName="story">
	<table>
		<tr>
			<td>Story Id</td>
			<td><form:input path="id" /></td>
		</tr>
		<tr>
			<td>Name</td>
			<td><form:input path="username" /></td>
		</tr>
		<tr>
			<td>Age</td>
			<td><form:input path="age" /></td>
		</tr>
		<tr>
			<td>Weight before</td>
			<td><form:input path="weightbefore" /></td>
		</tr>
		<tr>
			<td>Weight after</td>
			<td><form:input path="weightafter" /></td>
		</tr>
		<tr>
			<td>How she did it?</td>
			<td><form:input path="work" /></td>
		</tr>
		<tr>
			<td>tags</td>
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
	<th>Name</th>
	<th>Age</th>
	<th>Weight before</th>
	<th>Weight after</th>
	<th>Work</th>
	<th>Tags</th>
	<c:forEach items="${storyList}" var="story">
		<tr>
			<td>${story.id}</td>
			<td>${story.username}</td>
			<td>${story.age}</td>
			<td>${story.weightbefore}</td>
			<td>${story.weightafter}</td>
			<td>${story.work}</td>
			<td>${story.tags}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>