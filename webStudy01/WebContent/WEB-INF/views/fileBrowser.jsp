<%@page import="java.io.File"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function() {
	    var fileForm = document.fileForm;
		$("#fileList>li").on("dblclick", function() {
			alert($(this).text());
			fileForm.path.value=$(this).attr('value');
			fileForm.name.value=$(this).text();
			fileForm.submit();

		});
	})
	function name() {
		
	}
</script>
<title>Insert title here</title>
</head>
<body>

	<jsp:useBean id="file" class="java.util.ArrayList" scope="request" />
	<ul id="fileList">
		<%
		
			String pattern = "<li value=%s >%s</li>";
			for (Object name : file) {
				File ss = (File) name;
				out.println(String.format(pattern, ss.getAbsolutePath(), ss.getName()));
			}
		%>
	</ul>
	<form method="get" name="fileForm" >
	<input name="path" value="" type="hidden" />
	<input name="name" value="" type="hidden" />
	</form>
</body>
</html>