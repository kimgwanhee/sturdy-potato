<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>05/destination.jsp</title>
</head>
<body>
<h4>페이지 이동 후 최종 도착지</h4>
<pre>
	**11월13일-2
	페이지 이동 후 브라우저의 주소를 확인할 것.
	
	<%=pageContext.getAttribute("pageContextAttr") %>
	<%=request.getAttribute("requestAttr") %>
	<%=session.getAttribute("sessionAttr") %>
	<%=application.getAttribute("applicationAttr") %>	
	
	누적 방문자 수 - 누구나 볼수있어야함(어플리케이션 스코프)
	
	알바몬사이트 등록모듈 - 검증 통과못함 에러메시지 전송할 때 리퀘스트 스코프(적절하게 사라져야..)
					 - 성공했을 경우 리다이렉트로 이동하므로 세션스코프
					 
	접속자리스트 - 어플리케이션스코프(누구나 공유)
</pre>
</body>
</html>