<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page import="com.sdhan.project.model.QuestionSet"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Questions Page</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
</head>
<body>
<c:url var="addAction" value="/viewQuestions" ></c:url>
<form:form action="${addAction}" commandName="viewQuestions">
<c:if test="${!empty listQuestions}">
	<table class="tg">
		<tr>
			<th width="20" height= "30"> ID</th>
			<th width="20" height= "30">Chapter</th>
			<th width="20" height= "30">Bit No</th>
			<th width="60" height= "30">Complexity</th>
			<th width="120" height= "30">Question</th>
			<th width="30" height= "30">Option A</th>
			<th width="30" height= "30">Option B</th>
			<th width="30" height= "30">Option C</th>
			<th width="30" height= "30">Option D</th>
			<th width="30" height= "30">Correct Answer</th>
			<th width="30" height= "30">Delete</th>
		</tr>
		<c:forEach items="${listQuestions}" var="Question">
			<tr>	
			    <td>${Question.id}</td>
				<td>${Question.chapterNo}</td>
				<td>${Question.bitNo}</td>
				<td>${Question.complexity}</td>
				<td>${Question.questiontext}</td>
				<td>${Question.optiona}</td>
				<td>${Question.optionb}</td>
				<td>${Question.optionc}</td>
				<td>${Question.optiond}</td>
				<td>${Question.correctAnswer}</td>
				<td><a href="<c:url value='/remove/${Question.id}' />" >Delete</a></td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<c:if test="${empty listQuestions}">
	<center>	<label > <font size = 8 >No Questions in database</font></label> </center>
</c:if>
</form:form>
</body>
</html>