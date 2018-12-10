<%@page import="kr.or.ddit.mvc.annotation.URIMapping"%>
<%@page import="kr.or.ddit.board.controller.BoardListController"%>
<%@page import="kr.or.ddit.mvc.annotation.CommandHandler"%>
<%@page import="kr.or.ddit.utils.ReflectionUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%=ReflectionUtil.getClassesWithAnnotationAtBasePackages(CommandHandler.class, "kr.or.ddit.board.controller", "kr.or.ddit.prod.controller")%>
<hr/>

<%=ReflectionUtil.getMethodsWithAnnotationAtClass(BoardListController.class, URIMapping.class, String.class, HttpServletRequest.class, HttpServletResponse.class) %>
</body>
</html>