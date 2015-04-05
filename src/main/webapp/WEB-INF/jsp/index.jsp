<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Recommendation Engine</title>
</head>
<body>
<h1>Manage Repository</h1>
<br/>
<table>
	<tr>
	<td>Manage</td>
		<td>
		<form:form action="redirect2" method="GET">
		<input type="submit" name="action" value="message" />
		</form:form>
		</td>
		<td>
		<form:form action="redirect" method="GET">
<input type="submit" name="action" value="story" />
</form:form>
		</td>
	</tr>

</table>




</body>
</html>