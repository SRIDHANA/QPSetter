<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page session="false" %>
<html>
<head>
	<title>Question Paper Sets Generator</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
</head>
<body>
<%
	String simple = StringUtils.defaultIfBlank(request.getParameter("simple"), "");
	String medium = StringUtils.defaultIfBlank(request.getParameter("medium"), "") ;
	String hard = StringUtils.defaultIfBlank(request.getParameter("hard"), "") ;
	String noOfQuestions = StringUtils.defaultIfBlank(request.getParameter("noOfQuestions"),"");
	String noOfSets = StringUtils.defaultIfBlank(request.getParameter("noOfSets"), "") ;
	String questionFilePath = StringUtils.defaultIfBlank(request.getParameter("questionFilePath"), "") ;
	String questionsFileId = StringUtils.defaultIfBlank(request.getParameter("questionsFileId"), "") ;
	String chpWtgFilePath = StringUtils.defaultIfBlank(request.getParameter("chpWtgFilePath"), "") ;
	String chapterWtgesListStr = StringUtils.defaultIfBlank(request.getParameter("chapterWtgesListStr"), "") ;
	String errorMsg =  StringUtils.defaultIfBlank(request.getParameter("errorMsg"), "");
%>
	<h1>Question Paper Sets Generator</h1>
	<br/><br/>
	<form id="qpSetsGeneratorForm" name="qpSetsGeneratorForm" action="./qpsetsprocessor/process" method="post" enctype="multipart/form-data">
		<input type="hidden" id="action" name="action" value="" />
		<input type="hidden" id="questionsFileId" name="questionsFileId" value="<%=questionsFileId%>" />
		<input type="hidden" id="chapterWtgesListStr" name="chapterWtgesListStr" value="<%=chapterWtgesListStr%>" />
		<% if( ! StringUtils.isBlank(errorMsg)) { %>
		
			<table>
				<tr>
					<th>
		<% if( !errorMsg.equalsIgnoreCase("Executed Successfully")) { %>
				<font color="red">ERROR...!!</font>
				
	<%	}	%>
					</th>
				</tr>
		
				<tr>
					<td>
		<% if( !errorMsg.equalsIgnoreCase("Executed Successfully")) { %>
						<font color="red"><%=errorMsg%></font>
	<%	}
		else
		{%>
						<font color="green" size= 6 ><%=errorMsg%></font>
	<%	}%>
	
					</td>
				</tr>
			</table>
		<% } %>
		<table>
			<tr>
				<td><a href="./history">History</a></td>
			</tr>	
			<tr>
				<td><a href="./viewQuestions">View Uploaded Questions </a></td>
			</tr>
		</table>
		<br/>
		<br/>
		<table>
		
			<tr>
				<td>
					<label>View Database and Upload Only New Questions (Optional)</label>
				</td>
				<td>
					<input type="file" name="questionFile" value="<%= questionFilePath %>">
					<% if (!StringUtils.isBlank(questionsFileId)) { %>
						<label> File successfully Uploaded. </label>
					<% } %> 
					
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="Upload" onclick="javascript: document.getElementById('action').value='UPLOAD_QSTNS_FILE';" />
				</td>
			</tr>
		</table>
		
		<table>
			<tr>
				<td>
					<label>Upload Chapter Weightage File</label>
				</td>
				<td>
					<input type="file" name="chpWtgFile" value="<%= chpWtgFilePath %>">					
					<% if (!StringUtils.isBlank(chapterWtgesListStr)) { %>
						<label> File successfully Uploaded. </label>
					<% } %>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="Upload" onclick="javascript: document.getElementById('action').value='UPLOAD_CHP_WTGS_FILE';" />
					
				</td>
			</tr>
		</table>
		<br/>
		
		
		<table>
			<tr>
				<td>
					<h3>Complexity Percentages</h3>
				</td>
			</tr>
			<tr>
				<td>
					<label>Simple</label>
				</td>
				<td>
					<input type="text" name="simple" value="<%=simple%>" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Medium</label>
				</td>
				<td>
					<input type="text" name="medium" value="<%=medium%>" />
				</td>
			</tr>
			<tr>
				<td>
					<label>Hard</label>
				</td>
				<td>
					<input type="text" name="hard" value="<%=hard%>" />
				</td>
			</tr>
			</table>
			
			<br/>
			
			<table>
			<tr>
				<td>
					<label>No Of Questions per paper </label>
				</td>
				<td>
					<input type="text" name="noOfQuestions" value="<%=noOfQuestions%>" />
				</td>
			</tr>
			<tr>
				<td>
					<label>No Of Sets required</label>
				</td>
				<td>
					<input type="text" name="noOfSets" value="<%=noOfSets%>" />
				</td>
			</tr>
			</table>			
			<input type="submit" value="Generate Sets" onclick="javascript: document.getElementById('action').value='GENERATE_QNS_SETS';" />
	</form>
</body>