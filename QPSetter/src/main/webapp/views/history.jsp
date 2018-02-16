<%@page import="com.sdhan.project.model.QuestionSet"%>
<%@page import="java.util.List"%>
<html>
<head>
	<title>User Page</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
</head>
<body>
<h1>History of Generated Question Sets</h1>
	<%
		List<QuestionSet> questionSetList = (List<QuestionSet>)request.getAttribute("questionSetsList");
		if(questionSetList!=null && questionSetList.size() > 0) {
			%>
			<table>
	<tr>
		<td><a href="./">Home Page</a></td>
	</tr>
	<tr>
		<td>S.No</td>
		<td>QuestionSetCreatedOn</td>
		<td>Link</td>
	</tr>
			
		<%	for(QuestionSet qs: questionSetList) {
	%>
	<tr>
		<td><%=qs.getId() %></td>
		<td><%=qs.getCreatedOn() %></td>	
		<td><a href="./questionset/download/<%=qs.getId() %>">Download Set</a></td>
	</tr>
	<%
			}%>
			</table>
		<% }
		else
		{
	%>
	<label>No Sets found in database</label>
	<% 	}  %>
	
</body>