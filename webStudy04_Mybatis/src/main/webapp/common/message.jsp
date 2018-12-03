<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4> 공통 메시지 뷰 </h4>
<%
	String goLink = (String) session.getAttribute("goLink");
	String message = (String) session.getAttribute("message");
	if(StringUtils.isNotBlank(goLink) && StringUtils.isNotBlank(message)){
		%>
		<%=message %><br>
		<a href = "<%=request.getContextPath()%><%=goLink%>">요기로 가세요 </a>
		<%
		session.removeAttribute("message");//세션삭제
		session.removeAttribute("goLink");
	}
	if(new Boolean(true).equals(session.getAttribute("isRemoved"))){
		request.getSession().invalidate();//이제 세션만료
	}
%>

</body>
</html>