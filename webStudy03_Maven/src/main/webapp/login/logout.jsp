<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//11월14일 -6
// 	session.removeAttribute("authMember");
	//이동(index.jsp, redirect)

	session.invalidate();//세션스코프안에 있는 모든 객체 삭제 //요청이 들어오면 새로운 세션생성
	
	response.sendRedirect(request.getContextPath()+"/");
	//아래코드 ㅠ필요없음 jsp로 굳이할필요없이 서블릿사용하는게 좋다.
%>
