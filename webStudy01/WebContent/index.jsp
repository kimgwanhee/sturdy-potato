<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
	String mem_id = (String)session.getAttribute("authMember");
%>
<meta charset="UTF-8">
<title>/index.jsp</title>
</head>
<body>
<h4> 웰컴 페이지 </h4>
<pre>
	11월 14일 -7
	처음부터 웰컴 페이지로 접속하거나,
	로그인에 성공해서 웰컴 페이지로 접속하는 경우의 수가 있음.
	
	<%
		if(StringUtils.isNotBlank(mem_id)){
	%>
	
	<%=mem_id %>님로그인 상태, <a href = "<%=request.getContextPath()%>/login/logout.jsp">로그아웃</a>
	<%
		}else{
		%>
		<a href ="<%=request.getContextPath()%>/login/loginForm.jsp">로그인 하러가기</a>
		<%	
		}
	%>
</pre>
</body>
</html>