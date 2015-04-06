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
<h3>Manage Repository</h3>
<br/>
<table>
	<tr>
	<td>Manage</td>
		<td>
		<form:form action="redirect2" method="GET">
		<input type="submit" name="action" value="Persuasion Messages" />
		</form:form>
		</td>
		<td>
		<form:form action="redirect" method="GET">
<input type="submit" name="action" value="Success Stories" />
</form:form>
		</td>
	</tr>
</table>
<br/><br/>
<h3>Add-on messages</h3>
<table>
<form:form action="getMessage" method="POST" commandName="message">
	
		<tr>
			<td>Message Text</td>
			<td><form:input path="text" /></td>
		</tr>
		<tr>
			<td>Tags</td>
			<td><form:input path="tags" /></td>
		</tr>
		<tr> 
			<td>
			<br />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" name="action" value="Get Persuasion Message" />
			</td>
		</tr>
		<tr> 
			<td>
			<br /> 
			</td>
		</tr>
</form:form>
</table>

<table>
		<tr>
		<c:if test="${not empty message2}">
			<td>${message2}</td>
		</c:if>
		</tr>
</table>
<br /> <br />
<table>
<form:form action="getStory" method="POST" commandName="message">
			
		<tr>
			<td>Message Text</td>
			<td><form:input path="text" /></td>
		</tr>
		<tr>
			<td>Tags</td>
			<td><form:input path="tags" /></td>
		</tr>
		<tr> 
			<td>
			<br />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" name="action" value="Get Success Story" />
			</td>
		</tr>
</form:form>
</table>
<br/>
<table>
		<tr>
		<c:if test="${not empty storyText}">
			<td>${storyText}</td>
		</c:if>
		</tr>
</table>


</body>
</html>